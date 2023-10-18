package org.sesac.management.view.artist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentArtistEnrollBinding

class ArtistEnrollFragment :
    BaseFragment<FragmentArtistEnrollBinding>(FragmentArtistEnrollBinding::inflate) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentArtistEnrollBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated() {
        with(binding) {
            /* toolbar 아이콘, 텍스트 설정 */
            with(layoutToolbar) {
                ivBack.setImageResource(R.drawable.baseline_arrow_back_24)
                tvTitle.text = "아티스트"
                ivHamburger.setImageResource(R.drawable.baseline_menu_24)
                /* 뒤로가기 */
                ivBack.setOnClickListener {
                    requireFragmentManager().popBackStack()
                }
            }

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