package org.sesac.management.view.artist.enroll

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.room.Artist
import org.sesac.management.data.room.ArtistType
import org.sesac.management.databinding.FragmentArtistEnrollBinding
import java.util.Date

class ArtistEnrollFragment :
    BaseFragment<FragmentArtistEnrollBinding>(FragmentArtistEnrollBinding::inflate) {
    private val viewModel: ArtistEnrollViewModel by viewModels()

    private var debutDate = emptyList<String>()
    private var groupName = ""
    private var memberListString = ""
    private lateinit var artistType: ArtistType
    private var insertValue = emptyList<Long>()

    override fun onViewCreated() {
        initView()
        initTextWatcher()
        observeData()
    }

    private fun enrollArtist() {
        debutDate = binding.layoutInputDebut.tilEt.text.toString().split('-')
        groupName = binding.layoutInputName.tilEt.text.toString()
        memberListString = binding.layoutInputMember.tilEt.text.toString()
        artistType = when (binding.spinnerArtistType.selectedItem.toString()) {
            resources.getString(R.string.singer) -> ArtistType.SINGER
            resources.getString(R.string.actor) -> ArtistType.ACTOR
            resources.getString(R.string.comedian) -> ArtistType.COMEDIAN
            resources.getString(R.string.model) -> ArtistType.MODEL
            else -> ArtistType.NONE
        }

        // '저장'버튼 클릭시 각각의 입력값에 대한 유효성 검사
        if (checkValidationAndEnroll(debutDate, groupName, memberListString, artistType)) {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.insertArtist(
                    Artist(
                        groupName,
                        memberListString,
                        Date(),
                        artistType,
                        null,
                        "",
                        0
                    )
                )
            }
            showToastMessage(resources.getString(R.string.artist_enroll_success))
            // DB에 저장하고 popBackStack()
            parentFragmentManager.popBackStack()
        } else {
            showToastMessage(resources.getString(R.string.artist_enroll_error))
        }
    }

    private fun checkValidationAndEnroll(
        debutDate: List<String>,
        groupName: String,
        memberListString: String,
        artistType: ArtistType
    ): Boolean {
        // 각각의 입력값이 비어있거나 기본 값이면 false 반환
        return !(debutDate.isEmpty() || groupName.isEmpty() || memberListString.isEmpty() || artistType == ArtistType.NONE)
    }

    private fun observeData() {
        viewModel.insertArtist.observe(viewLifecycleOwner) {
            insertValue = it
        }
    }

    private fun initView() {
        with(binding) {
            /* toolbar 아이콘, 텍스트 설정 */
            layoutToolbar.setToolbarMenu(resources.getString(R.string.artist_title), true)
            layoutInputDebut.tilLayout.hint = resources.getString(R.string.artist_debut_date)
            layoutInputDebut.tilLayout.helperText =
                resources.getString(R.string.artist_debut_helper)

            layoutInputName.tilLayout.hint = resources.getString(R.string.artist_name)
            layoutInputName.tilLayout.helperText = resources.getString(R.string.artist_name_helper)

            layoutInputMember.tilLayout.hint = resources.getString(R.string.artist_member)
            layoutInputMember.tilLayout.helperText =
                resources.getString(R.string.artist_member_helper)

            spinnerArtistType.adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.artist_types,
                android.R.layout.simple_list_item_1
            )
            /* 취소 버튼 */
            btnCancel.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            /* 저장 버튼 */
            btnSave.setOnClickListener {
                enrollArtist()
            }
        }
    }

    private fun initTextWatcher() {
        with(binding) {
            val textInputLayoutDebut = layoutInputDebut.tilLayout
            val textInputLayoutName = layoutInputName.tilLayout
            val textInputLayoutMember = layoutInputMember.tilLayout

            addTextInputLayoutWithTextWatcher(textInputLayoutDebut, debutTextWatcher)
            addTextInputLayoutWithTextWatcher(textInputLayoutName, nameTextWatcher)
            addTextInputLayoutWithTextWatcher(textInputLayoutMember, memberTextWatcher)
        }
    }

    private val debutTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            val inputText = s.toString()
            val dateRegex = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$"
            if (inputText.isEmpty()) {
                binding.layoutInputDebut.tilLayout.error =
                    resources.getString(R.string.artist_error_debut_empty)
            } else {
                if (inputText.matches(Regex(dateRegex))) {
                    binding.layoutInputDebut.tilLayout.error = null
                } else {
                    binding.layoutInputDebut.tilLayout.error =
                        resources.getString(R.string.artist_error_debut_not_valid)
                }
            }
        }
    }

    private val nameTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            val inputText = s.toString()
            if (inputText.isEmpty()) {
                binding.layoutInputName.tilLayout.error =
                    resources.getString(R.string.artist_error_name_empty)
            }
        }
    }

    private val memberTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            val inputText = s.toString()
            if (inputText.isEmpty()) {
                binding.layoutInputMember.tilLayout.error =
                    resources.getString(R.string.artist_error_member_empty)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // view가 destroy되면 TextWatcher와 TextInputLayout 연결 해제
        removeTextWatcherFromTextInputLayout(debutTextWatcher)
        removeTextWatcherFromTextInputLayout(nameTextWatcher)
        removeTextWatcherFromTextInputLayout(memberTextWatcher)
    }
}