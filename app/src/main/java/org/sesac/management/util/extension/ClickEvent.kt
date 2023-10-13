package org.sesac.management.util.extension

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.google.android.material.datepicker.MaterialTextInputPicker
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

const val INTERVAL_TIME = 250L
fun View.flowClicks(): Flow<Unit> = callbackFlow {
    setOnClickListener {
        trySend(Unit).isSuccess
    }
    awaitClose { setOnClickListener(null) }
}.buffer(0)

fun <T> Flow<T>.throttleFirst(intervalTime: Long): Flow<T> = flow {
    var throttleTime = 0L
    collect { upStream ->
        val currentTime = System.currentTimeMillis()
        if ((currentTime - throttleTime > intervalTime)) {
            throttleTime = currentTime
            emit(upStream)
        }
    }
}

fun View.setCoroutineFlowClickAction(
    intervalTime: Long = INTERVAL_TIME,
    scope: CoroutineScope,
    onClick: () -> Unit,
) {
    flowClicks()
        .throttleFirst(intervalTime)
        .onEach {
            CoroutineScope(Dispatchers.Main).launch {
                onClick.invoke()
            }
//            onClick.invoke()
        }
        .launchIn(scope)
}

open class FlowTextWatcher : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun afterTextChanged(p0: Editable?) {}
}

fun TextInputEditText.flowTextWatcher(): Flow<String> = callbackFlow {
    val flowTextWatcher = object : FlowTextWatcher() {
        override fun afterTextChanged(editable: Editable?) {
            trySend(editable.toString())
        }
    }
    addTextChangedListener(flowTextWatcher)
    awaitClose {
        removeTextChangedListener(flowTextWatcher)
    }
}
