package org.sesac.management.view.artist

import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentArtistEnrollBinding

class ArtistEnrollFragment :
    BaseFragment<FragmentArtistEnrollBinding>(FragmentArtistEnrollBinding::inflate) {
    override fun onViewCreated() {
        with(binding) {
            /* toolbar 아이콘, 텍스트 설정 */
            layoutToolbar.setToolbarMenu("아티스트 등록", true)

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