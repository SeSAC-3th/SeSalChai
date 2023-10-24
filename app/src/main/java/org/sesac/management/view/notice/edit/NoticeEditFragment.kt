package org.sesac.management.view.notice.edit

import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentNoticeEditBinding

class NoticeEditFragment
    : BaseFragment<FragmentNoticeEditBinding>(FragmentNoticeEditBinding::inflate) {
    override fun onViewCreated() {
        with(binding) {
            toolbarNoticeEdit.setToolbarMenu("공지사항 수정",true)
        }
    }
}