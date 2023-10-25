package org.sesac.management.view.artist.edit

import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Artist
import org.sesac.management.data.local.ArtistType
import org.sesac.management.databinding.FragmentArtistEditBinding
import org.sesac.management.util.common.ioScope
import org.sesac.management.util.common.showToastMessage
import org.sesac.management.util.extension.afterTextChangesInFlow
import org.sesac.management.util.extension.focusChangesInFlow
import org.sesac.management.util.extension.initInFlow
import org.sesac.management.view.artist.ArtistViewModel
import reactivecircus.flowbinding.android.widget.AfterTextChangeEvent
import java.util.Date

class ArtistEditFragment :
    BaseFragment<FragmentArtistEditBinding>(FragmentArtistEditBinding::inflate) {
    private val viewModel: ArtistViewModel by viewModels()

    private var insertValue = emptyList<Long>()

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

        // '저장'버튼 클릭시 각각의 입력값에 대한 유효성 검사
        if (checkValidationAndEnroll(debutDate, groupName, memberListString, artistType)) {
            ioScope.launch {
//                viewModel.insertArtist(
//                    Artist(
//                        groupName,
//                        memberListString,
//                        Date(),
//                        artistType,
//                        null,
//                        "Url",
//                        0
//                    )
//                )
            }
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

    // 각각의 입력값이 비어있거나 기본 값이면 false 반환
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

    private fun initView() {
        with(binding) {
            /* toolbar 아이콘, 텍스트 설정 */
            layoutToolbar.setToolbarMenu(resources.getString(R.string.artist_edit_title), true) {
                // 클릭 이벤트
            }
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

            spinnerArtistType.adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.artist_types,
                android.R.layout.simple_list_item_1
            )
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