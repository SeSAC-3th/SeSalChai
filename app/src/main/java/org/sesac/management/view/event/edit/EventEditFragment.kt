package org.sesac.management.view.event.edit

import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Event
import org.sesac.management.data.util.convertUriToBitmap
import org.sesac.management.databinding.FragmentEventEditBinding
import org.sesac.management.util.common.ARTIST
import org.sesac.management.util.common.ioScope
import org.sesac.management.util.common.showToastMessage
import org.sesac.management.util.extension.afterTextChangesInFlow
import org.sesac.management.util.extension.focusChangesInFlow
import org.sesac.management.util.extension.initInFlow
import org.sesac.management.view.event.EventViewModel
import reactivecircus.flowbinding.android.widget.AfterTextChangeEvent


class EventEditFragment :
    BaseFragment<FragmentEventEditBinding>(FragmentEventEditBinding::inflate) {
    val eventViewModel: EventViewModel by activityViewModels()
    private lateinit var selectedEvent: Event
    private var eventDescription: String = ""

    /**
     * Bitmap
     * @author 민서
     */
    /* 선택한 이미지 절대경로 가져오기 */
    //* bitmap을 insert할때 넘겨주면 됩니다
    private var bitmap: Bitmap? = null
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                Log.d(ARTIST, "uri: $uri")
                binding.ivEvent.setImageURI(uri)
                context?.let { it1 ->
                    var tmpBitmap = convertUriToBitmap(uri, it1)
                    tmpBitmap?.let { it2 -> bitmap = it2 }
                }
            }
        }

    override fun onViewCreated() {
        initView()
        initTextWatcher()
        with(binding) {
            tbScheduleEnroll.setToolbarMenu("행사 수정", true) {
                updateEvent()
            }

            ivEvent.setOnClickListener {
                getContent.launch("image/*")
            }
        }
    }

    /**
     * Init view
     * 초기화면 setting (선택된 Event의 데이터 + TexitInput Layout 가이드)
     */
    private fun initView() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                eventViewModel.eventDetail.collect { event ->
                    selectedEvent = event
                    // 선택된 이벤트 내용을 채우는 메서드
                    updateUI()
                }
            }
        }

        with(binding) {
            layoutInputName.tilLayout.initInFlow(
                "행사명",
                "행사명을 입력해주세요"
            )
            layoutInputDate.tilLayout.initInFlow(
                "일시",
                resources.getString(R.string.artist_debut_helper)
            )
            layoutInputPlace.tilLayout.initInFlow(
                "행사 장소",
                "장소를 입력해주세요"
            )
            layoutInputDescription.tilLayout.initInFlow(
                "상세 내용",
                "상세 정보를 입력해주세요"
            )
        }
    }

    private fun updateUI() {
        with(binding) {
            ivEvent.setImageBitmap(selectedEvent.imgUri)
            layoutInputName.tilEt.setText(selectedEvent.name)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                layoutInputDate.tilEt.text = Editable.Factory.getInstance()
                    .newEditable(SimpleDateFormat("yyyy-MM-dd").format(selectedEvent.date))
            }

            layoutInputPlace.tilEt.setText(selectedEvent.place)
            layoutInputDescription.tilEt.setText(selectedEvent.description)
        }
    }

    /**
     * Update event
     * 입력한 Data로 데이터를 갱신하는 메서드
     */
    private fun updateEvent() {
        val eventName = binding.layoutInputName.tilEt.text.toString()
        val eventPlace = binding.layoutInputPlace.tilEt.text.toString()
        val eventDate = binding.layoutInputDate.tilEt.text.toString().split('-')
        val eventDescription = binding.layoutInputDescription.tilEt.text.toString()

        val dateFormat = java.text.SimpleDateFormat("yyyy-MM-dd")
        val date = dateFormat.parse(binding.layoutInputDate.tilEt.text.toString())

        if (bitmap == null) {
            bitmap = selectedEvent.imgUri
        }

        // 입력 형태에 대한 유효성 검사
        if (checkValidationAndEnroll(eventName, eventPlace, eventDate, eventDescription)) {
            ioScope.launch {
                // TODO : 여기를 updateEvent로 변경
                eventViewModel.updateEvent(
                    Event(
                        eventName,
                        eventPlace,
                        date,
                        eventDescription,
                        bitmap,
                        eventViewModel.eventDetail.value!!.eventId
                    )
                )
            }
            showToastMessage(resources.getString(R.string.event_enroll_success))
            // DB에 저장하고 popBackStack()
            backPress()
        } else {
            with(binding) {
                if (layoutInputDate.tilEt.text.toString().isEmpty()) {
                    layoutInputDate.tilLayout.error =
                        resources.getString(R.string.event_error_date_empty)
                } else if (layoutInputName.tilEt.text.toString().isEmpty()) {
                    layoutInputName.tilLayout.error =
                        resources.getString(R.string.event_error_name_empty)
                } else {
                    // 스피너를 선택 안했을 때
                }
            }
            showToastMessage(resources.getString(R.string.event_enroll_error))
        }
    }

    // 각각의 입력값이 비어있거나 기본 값이면 false 반환
    private fun checkValidationAndEnroll(
        eventName: String,
        eventPlace: String,
        eventDate: List<String>,
        eventDescription: String,
    ) =
        !(eventName.isEmpty() || eventPlace.isEmpty() || eventDate.isEmpty() || eventDescription.isEmpty())


    private fun initTextWatcher() {
        with(binding) {

            layoutInputName.tilLayout.afterTextChangesInFlow(inputName)
            layoutInputName.tilLayout.focusChangesInFlow(hasFocus)

            layoutInputDate.tilLayout.afterTextChangesInFlow(inputDate)
            layoutInputDate.tilLayout.focusChangesInFlow(hasFocus)

            layoutInputPlace.tilLayout.afterTextChangesInFlow(inputPlace)
            layoutInputPlace.tilLayout.focusChangesInFlow(hasFocus)

            layoutInputDescription.tilLayout.afterTextChangesInFlow(inputDescription)
            layoutInputDescription.tilLayout.focusChangesInFlow(hasFocus)
        }
    }


    private val inputDate = { layout: TextInputLayout, event: AfterTextChangeEvent ->
        val inputText = event.editable.toString()
        val dateRegex = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$"
        if (inputText.isEmpty()) {
            layout.initInFlow(
                resources.getString(R.string.event_date),
                resources.getString(R.string.event_date_helper)
            )
        } else {
            if (inputText.matches(Regex(dateRegex))) {
                layout.error = null
            } else {
                layout.error = resources.getString(R.string.event_error_date_not_valid)
            }
        }
    }

    private val inputName = { layout: TextInputLayout, event: AfterTextChangeEvent ->
        val inputText = event.editable.toString()
        if (inputText.isEmpty()) {
            layout.initInFlow(
                resources.getString(R.string.event_name), ""
            )
        }
    }

    private val inputPlace = { layout: TextInputLayout, event: AfterTextChangeEvent ->
        val inputText = event.editable.toString()
        if (inputText.isEmpty()) {
            layout.initInFlow(
                resources.getString(R.string.event_place), ""
            )
        }
    }

    private val inputDescription = { layout: TextInputLayout, event: AfterTextChangeEvent ->
        val inputText = event.editable.toString()
        if (inputText.isEmpty()) {
            layout.initInFlow(
                resources.getString(R.string.event_description), ""
            )
        }
    }

    private val hasFocus =
        { layout: TextInputLayout, hasFocus: Boolean -> if (hasFocus) layout.error = null }

}