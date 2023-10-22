package org.sesac.management.view.artist.enroll

import androidx.fragment.app.viewModels
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentArtistEnrollBinding

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

            layoutInputMember.tilLayout.hint = resources.getString(R.string.artist_member)
            layoutInputMember.tilLayout.helperText =
                resources.getString(R.string.artist_member_helper)

            /* 취소 버튼 */
            with(btnCancel) {
//                parentFragmentManager.popBackStack()
            }

            /* 저장 버튼 */
            with(btnSave) {
//                parentFragmentManager.popBackStack()
            }
        }
    }
}