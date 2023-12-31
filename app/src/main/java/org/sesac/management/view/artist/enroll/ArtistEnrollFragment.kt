package org.sesac.management.view.artist.enroll

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputLayout
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Artist
import org.sesac.management.data.local.ArtistType
import org.sesac.management.data.util.convertUriToBitmap
import org.sesac.management.databinding.FragmentArtistEnrollBinding
import org.sesac.management.util.common.ARTIST
import org.sesac.management.util.common.showToastMessage
import org.sesac.management.util.extension.afterTextChangesInFlow
import org.sesac.management.util.extension.focusChangesInFlow
import org.sesac.management.util.extension.initInFlow
import org.sesac.management.view.artist.ArtistViewModel
import reactivecircus.flowbinding.android.widget.AfterTextChangeEvent
import java.text.SimpleDateFormat

/**
 * Artist enroll fragment
 *
 * @constructor Create empty Artist enroll fragment
 * @author 우빈
 */
class ArtistEnrollFragment :
    BaseFragment<FragmentArtistEnrollBinding>(FragmentArtistEnrollBinding::inflate) {
    private val viewModel: ArtistViewModel by activityViewModels()

    private var insertValue = emptyList<Long>()
    private var bitmap: Bitmap? = null

    /**
     * getContet의 image/*를 호출하고 callback 재정의
     * uri로부터 binding을 초기화해주고
     * [converUriToBitmap] 함수로 Bitmap 변환후 RoomDB에 저장
     * 저장하는 변수는 bitmap
     * @author 민서
    */ */
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                Log.d(ARTIST, "uri: $uri")
                binding.ivArtist.setImageURI(uri)
                context?.let { it1 ->
                    var tmpBitmap = convertUriToBitmap(uri, it1)
                    tmpBitmap?.let { it2 -> bitmap = it2 }
                }
            }
        }

    override fun onViewCreated() {
        initView()
        initTextWatcher()
        observeData()
    }

    private fun enrollArtist() {
        val debutDate = binding.layoutInputDebut.tilEt.text.toString().split('-')
        val groupName = binding.layoutInputName.tilEt.text.toString()
        val memberListString = binding.layoutInputMember.tilEt.text.toString()
        val artistType = when (binding.spinnerArtistType.selectedItem.toString()) {
            resources.getString(R.string.singer) -> ArtistType.SINGER
            resources.getString(R.string.actor) -> ArtistType.ACTOR
            resources.getString(R.string.comedian) -> ArtistType.COMEDIAN
            resources.getString(R.string.model) -> ArtistType.MODEL
            else -> ArtistType.NONE
        }

        val dateFormat =
            SimpleDateFormat("yyyy-MM-dd")
        val date = dateFormat.parse(binding.layoutInputDebut.tilEt.text.toString())

        /**
         * '저장'버튼 클릭시 각각의 입력값에 대한 유효성 검사
         * @author 우빈
         */
        if (checkValidationAndEnroll(debutDate, groupName, memberListString, artistType)) {
            viewModel.insertArtist(
                Artist(
                    groupName,
                    memberListString,
                    date,
                    artistType,
                    null,
                    bitmap,
                    0
                )
            )
            showToastMessage(resources.getString(R.string.artist_enroll_success))
            // DB에 저장하고 popBackStack()
            backPress()
        } else {
            with(binding) {
                if (layoutInputDebut.tilEt.text.toString().isEmpty()) {
                    layoutInputDebut.tilLayout.error =
                        resources.getString(R.string.artist_error_debut_empty)
                } else if (layoutInputName.tilEt.text.toString().isEmpty()) {
                    layoutInputName.tilLayout.error =
                        resources.getString(R.string.artist_error_name_empty)
                } else if (layoutInputMember.tilEt.text.toString().isEmpty()) {
                    layoutInputMember.tilLayout.error =
                        resources.getString(R.string.artist_error_member_empty)
                } else {
                    // 스피너를 선택 안했을 때
                }
            }
            showToastMessage(resources.getString(R.string.artist_enroll_error))
        }
    }

    /**
     * 각각의 입력값이 비어있거나 기본 값이면 false 반환
     * @author 우빈
     */
    private fun checkValidationAndEnroll(
        debutDate: List<String>,
        groupName: String,
        memberListString: String,
        artistType: ArtistType,
    ) =
        !(debutDate.isEmpty() || groupName.isEmpty() || memberListString.isEmpty() || artistType == ArtistType.NONE)


    private fun observeData() {
        viewModel.insertArtist.observe(viewLifecycleOwner) {
            insertValue = it
        }
    }

    /**
     * 초기 View 세팅하는 함수
     * @author 우빈
     */
    private fun initView() {
        with(binding) {
            /* toolbar 아이콘, 텍스트 설정 */
            layoutToolbar.setToolbarMenu(resources.getString(R.string.artist_title), true)
            layoutInputDebut.tilLayout.initInFlow(
                resources.getString(R.string.artist_debut_date),
                resources.getString(R.string.artist_debut_helper)
            )
            layoutInputName.tilLayout.initInFlow(
                resources.getString(R.string.artist_name),
                resources.getString(R.string.artist_name_helper)
            )
            layoutInputMember.tilLayout.initInFlow(
                resources.getString(R.string.artist_member),
                resources.getString(R.string.artist_member_helper)
            )
            /* image 설정 */
            ivArtist.setOnClickListener {
                getContent.launch("image/*")
            }
            spinnerArtistType.adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.artist_types,
                android.R.layout.simple_list_item_1
            )

            /* 취소 버튼 */
            binding.btnCancel.setOnClickListener {
                backPress()
            }

            /* 저장 버튼 */
            binding.btnSave.setOnClickListener {
                enrollArtist()
            }
        }
    }

    private fun initTextWatcher() {
        with(binding) {
            layoutInputDebut.tilLayout.afterTextChangesInFlow(inputDebut)
            layoutInputDebut.tilLayout.focusChangesInFlow(hasFocus)

            layoutInputName.tilLayout.afterTextChangesInFlow(inputName)
            layoutInputName.tilLayout.focusChangesInFlow(hasFocus)

            layoutInputMember.tilLayout.afterTextChangesInFlow(inputMember)
            layoutInputMember.tilLayout.focusChangesInFlow(hasFocus)
        }

    }

    /**
     * 각각의 TextInputLayout들에 대해 TextWatcher와 정규식을 적용
     * @author 우빈
     */
    private val inputDebut = { layout: TextInputLayout, event: AfterTextChangeEvent ->
        val inputText = event.editable.toString()
        val dateRegex = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$"
        if (inputText.isEmpty()) {
            layout.initInFlow(
                resources.getString(R.string.artist_debut_date),
                resources.getString(R.string.artist_debut_helper)
            )
        } else {
            if (inputText.matches(Regex(dateRegex))) {
                layout.error = null
            } else {
                layout.error = resources.getString(R.string.artist_error_debut_not_valid)
            }
        }
    }

    private val inputName = { layout: TextInputLayout, event: AfterTextChangeEvent ->
        val inputText = event.editable.toString()
        if (inputText.isEmpty()) {
            layout.initInFlow(
                resources.getString(R.string.artist_name),
                resources.getString(R.string.artist_name_helper)
            )
        }
    }

    private val inputMember = { layout: TextInputLayout, event: AfterTextChangeEvent ->
        val inputText = event.editable.toString()
        if (inputText.isEmpty()) {
            layout.initInFlow(
                resources.getString(R.string.artist_member),
                resources.getString(R.string.artist_member_helper)
            )
        }
    }

    private val hasFocus =
        { layout: TextInputLayout, hasFocus: Boolean -> if (hasFocus) layout.error = null }
}