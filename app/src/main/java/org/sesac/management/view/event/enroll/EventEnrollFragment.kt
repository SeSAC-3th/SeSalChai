package org.sesac.management.view.event.enroll

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Event
import org.sesac.management.data.model.DialogItem
import org.sesac.management.data.util.convertUriToBitmap
import org.sesac.management.databinding.FragmentEventEnrollBinding
import org.sesac.management.util.common.ARTIST
import org.sesac.management.util.common.ioScope
import org.sesac.management.util.common.showToastMessage
import org.sesac.management.util.extension.afterTextChangesInFlow
import org.sesac.management.util.extension.focusChangesInFlow
import org.sesac.management.util.extension.initInFlow
import org.sesac.management.view.event.EventViewModel
import org.sesac.management.view.event.dialog.ArtistAddDialogFragment
import org.sesac.management.view.event.dialog.DialogDataListener
import reactivecircus.flowbinding.android.widget.AfterTextChangeEvent
import java.util.Date

class EventEnrollFragment :
    BaseFragment<FragmentEventEnrollBinding>(FragmentEventEnrollBinding::inflate),
    DialogDataListener {
    val eventViewModel: EventViewModel by viewModels({ requireParentFragment() })
    private var selectedArtists = mutableListOf<DialogItem>()
    val TAG: String = "로그"
    private var eventDescription: String = ""

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
    /* 선택한 이미지 Uri 처리 */

    override fun onViewCreated() {
//        getArtistInfo()
        initTextWatcher()
        with(binding) {
            tbScheduleEnroll.setToolbarMenu("행사 등록", true) {
                enrollEvent()
            }

            ivEvent.setOnClickListener {
                getContent.launch("image/*")
            }
            /* 참여 아티스트 */
            with(tvJoinArtist) {
                setOnAvoidDuplicateClick {
                    requireActivity().let {
                        val addDialog = ArtistAddDialogFragment()
                        addDialog.onDialogDataSelected(this@EventEnrollFragment)
                        addDialog.show(childFragmentManager, "artistDialogFragment")
                    }
                }
            }
        }
    }

    private fun enrollEvent() {
        val eventName = binding.layoutInputName.tilEt.text.toString()
        val eventPlace = binding.layoutInputPlace.tilEt.text.toString()
        val eventDate = binding.layoutInputDate.tilEt.text.toString().split('-')

        // '저장'버튼 클릭시 각각의 입력값에 대한 유효성 layoutInputDate 검사
        if (checkValidationAndEnroll(eventName, eventPlace, eventDate, eventDescription)) {
            ioScope.launch {
                eventViewModel.insertEvent(
                    Event(
                        eventName,
                        eventPlace,
                        Date(),
                        eventDescription,
                        bitmap
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

            layoutInputPlace.tilLayout.afterTextChangesInFlow(inputDebut)
            layoutInputPlace.tilLayout.focusChangesInFlow(hasFocus)

            layoutInputDate.tilLayout.afterTextChangesInFlow(inputMember)
            layoutInputDate.tilLayout.focusChangesInFlow(hasFocus)
        }

    }

    private val inputDebut = { layout: TextInputLayout, event: AfterTextChangeEvent ->
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

    private val inputMember = { layout: TextInputLayout, event: AfterTextChangeEvent ->
        val inputText = event.editable.toString()
        if (inputText.isEmpty()) {
            layout.initInFlow(
                resources.getString(R.string.event_place), ""
            )
        }
    }

    private val hasFocus =
        { layout: TextInputLayout, hasFocus: Boolean -> if (hasFocus) layout.error = null }


    /**
     * ArtistAddDialogFragment로 부터 넘겨온 Artist 목록 List
     *
     * @param checkedList
     */
    override fun onDialogDataSelected(checkedList: MutableList<DialogItem>) {
        selectedArtists = checkedList
        val artistNameList: List<String> = selectedArtists.map { it.artistName }
        eventDescription = artistNameList.joinToString(", ")
    }
}