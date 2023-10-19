package org.sesac.management.view.notice

import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentNoticeDetailBinding

class NoticeDetailFragment() :
    BaseFragment<FragmentNoticeDetailBinding>(FragmentNoticeDetailBinding::inflate) {
    // 뒤로가기 버튼을 눌렀을 때를 위한 callback 변수

    override fun onViewCreated() {
        with(binding) {
            toolbarNoticeDetail.tvTitle.text = "공지사항"
        }
    }


}