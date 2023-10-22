package org.sesac.management.view.artist.enroll

import android.util.Log
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.room.Artist
import org.sesac.management.data.room.ArtistType
import org.sesac.management.databinding.FragmentArtistEnrollBinding
import java.util.Date

class ArtistEnrollFragment :
    BaseFragment<FragmentArtistEnrollBinding>(FragmentArtistEnrollBinding::inflate) {
    private val viewModel: ArtistEnrollViewModel by viewModels()

    override fun onViewCreated() {
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
            with(btnCancel) {
//                parentFragmentManager.popBackStack()
            }

            /* 저장 버튼 */
            btnSave.setOnClickListener {
                enrollArtist()
            }
        }
    }

    private fun enrollArtist() {
        val debutDate = binding.layoutInputDebut.tilEt.text.toString().split('-')
        val groupName = binding.layoutInputName.tilEt.text.toString()
        val memberListString = binding.layoutInputMember.tilEt.toString()
        val artistType = when (binding.spinnerArtistType.selectedItem.toString()) {
            "가수" -> ArtistType.SINGER
            "배우" -> ArtistType.ACTOR
            "코미디언" -> ArtistType.COMEDIAN
            "모델" -> ArtistType.MODEL
            else -> ArtistType.NONE
        }

        Log.d("asdf", debutDate.toString())
        viewModel.insertArtist(
            Artist(
                groupName,
                memberListString,
                Date(debutDate[0].toInt(), debutDate[1].toInt(), debutDate[2].toInt()),
                artistType,
                null,
                "",
                0
            )
        )
    }
}