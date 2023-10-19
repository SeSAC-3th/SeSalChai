package org.sesac.management.view.notice

import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentNoticeEnrollBinding

class NoticeEnrollFragment() :
    BaseFragment<FragmentNoticeEnrollBinding>(FragmentNoticeEnrollBinding::inflate) {


    override fun onViewCreated() {
        with(binding) {
            //toolbar 제목 설정
            toolbarNoticeEnroll.tvTitle.text = "공지사항 등록"

            //취소 버튼 클릭시 backKey와 동일하게 작동
            btnCancel.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

        }
    }


}