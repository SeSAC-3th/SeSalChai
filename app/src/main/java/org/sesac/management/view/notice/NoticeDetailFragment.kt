package org.sesac.management.view.notice

import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentNoticeDetailBinding

class NoticeDetailFragment() :
    BaseFragment<FragmentNoticeDetailBinding>(FragmentNoticeDetailBinding::inflate) {
    override fun onViewCreated() {
        with(binding) {
            toolbarNoticeDetail.setToolbarMenu("공지사항 상세", true)
        }
    }

}