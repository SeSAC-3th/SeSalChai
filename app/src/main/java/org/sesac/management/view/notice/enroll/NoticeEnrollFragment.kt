package org.sesac.management.view.notice.enroll

import androidx.fragment.app.viewModels
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Notice
import org.sesac.management.databinding.FragmentNoticeEnrollBinding
import org.sesac.management.view.notice.NoticeViewModel
import java.util.Date

class NoticeEnrollFragment() :
    BaseFragment<FragmentNoticeEnrollBinding>(FragmentNoticeEnrollBinding::inflate) {

    private val noticeViewModel: NoticeViewModel by viewModels()
    override fun onViewCreated() {

        with(binding) {
            toolbarNoticeEnroll.setToolbarMenu("공지사항 등록", true) {
                binding.toolbarNoticeEnroll.ivHamburger.setImageResource(R.drawable.baseline_edit_24)
                val notice = Notice(
                    binding.layoutInputTitle.tilEt.text.toString(),
                    binding.layoutInputContent.tilEt.text.toString(),
                    Date(),
                )
                noticeViewModel.insertNoticeInfo(notice)
                backPress()
            }
        }
    }

    private fun initView() {
        with(binding) {

        }
    }

}