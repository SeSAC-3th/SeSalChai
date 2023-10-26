package org.sesac.management.util.extension

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sesac.management.util.common.CLICK_INTERVAL_TIME
import org.sesac.management.util.common.INPUT_COMPLETE_TIME
import org.sesac.management.util.common.mainScope
import reactivecircus.flowbinding.android.view.clicks
import reactivecircus.flowbinding.android.view.focusChanges
import reactivecircus.flowbinding.android.widget.AfterTextChangeEvent
import reactivecircus.flowbinding.android.widget.afterTextChanges

/**
 * Set on finish input flow, textview 입력 완성 후 이벤트 처리 메서드
 *
 * flow의 debounce() 안에 sleep 타임은 1초로 설정되어 있음, 1초 뒤에야 현재의 text를 반환 받을 수 있다.
 *
 * flowBinding으로 구현
 * @param actionInMainThread
 * @receiver
 * @author 진혁
 */
fun TextView.setOnFinishInputFlow(actionInMainThread: (completedText: String) -> Unit) {
    this.afterTextChanges()
        .flowOn(Dispatchers.Main) // afterTextChanges()를 main에서 실행
        .debounce(INPUT_COMPLETE_TIME) // debounce()를 io에서 실행
        .flowOn(Dispatchers.IO)
        .onEach {// onEach{}를 main에서 실행
            actionInMainThread(it.view.text.toString())
        }.launchIn(mainScope)
}

/**
 * Set on avoid duplicate click, view에 대한 중복 클릭 방지 이벤트 처리 메서드
 *
 * throttleFrist() 안에 sleep 타임은 0.3초로 설정되어 있음, 0.3초간 클릭 못함
 * @param actionInMainThread
 * @receiver
 * @author 진혁
 */
fun View.setOnAvoidDuplicateClickFlow(actionInMainThread: () -> Unit) {
    this.clicks()
        .flowOn(Dispatchers.Main) // 이후 chain의 메서드들은 쓰레드 io 영역에서 처리
        .throttleFirst(CLICK_INTERVAL_TIME)
        .flowOn(Dispatchers.IO) // 이후 chain의 메서드들은 쓰레드 main 영역에서 처리
        .onEach {// onEach{}를 main에서 실행
            actionInMainThread()
        }.launchIn(mainScope)
}

// throttleFirst()는 Flow에 없기 때문에 직접 구현해줘야 한다. debounce()는 있다.
private fun <T> Flow<T>.throttleFirst(intervalTime: Long): Flow<T> = flow {
    var throttleTime = 0L
    collect { upStream ->
        val currentTime = System.currentTimeMillis()
        if ((currentTime - throttleTime) > intervalTime) {
            throttleTime = currentTime
            emit(upStream)
        }
    }
}

/**
 * add fragment, fragment 추가 메서드
 *
 * binding.layout.changeFragment(this, XXXFragment())
 *
 * @param from : 현재 fragment
 * @param to : 추가하고 싶은 fragment
 * @author 진혁
 */
fun ViewGroup.changeFragment(from: Fragment, to: Fragment) {
    from.childFragmentManager
        .beginTransaction()
        .add(this.id, to)
        .addToBackStack(null)
        .commitAllowingStateLoss()
}

/**
 * TextInputLayout Extension
 *
 * @param actionInMainThread, 이벤트 발생 시 동작하는 고차 함수
 * @receiver
 * @author 진혁
 */
// editText의 변화가 일어난 뒤의 동작을 설정하는 메서드
fun TextInputLayout.afterTextChangesInFlow(actionInMainThread: (TextInputLayout, AfterTextChangeEvent) -> Unit) {
    if (this.editText != null) {
        this.editText!!.afterTextChanges()
            .onEach { event ->
                actionInMainThread(this, event)
            }
            .launchIn(mainScope)
    }
}

// editText의 포커스가 바꼈을 때 동작을 설정하는 메서드
fun TextInputLayout.focusChangesInFlow(actionInMainThread: (TextInputLayout, Boolean) -> Unit) {
    if (this.editText != null) {
        this.editText!!.focusChanges()
            .onEach {
                actionInMainThread(this, it)
            }.launchIn(mainScope)
    }
}

// TextInputLayout의 hint와 helperText를 설정하는 메서드
fun TextInputLayout.initInFlow(hint: String, helperText: String) {
    this.hint = hint
    this.helperText = helperText
}