package org.sesac.management.view.notice.edit

import androidx.fragment.app.viewModels
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Notice
import org.sesac.management.databinding.FragmentNoticeEditBinding
import org.sesac.management.util.extension.initInFlow
import org.sesac.management.view.notice.NoticeViewModel
import java.util.Date

class NoticeEditFragment
    : BaseFragment<FragmentNoticeEditBinding>(FragmentNoticeEditBinding::inflate) {

    private val sharedViewModel: NoticeViewModel
            by viewModels(ownerProducer = { requireParentFragment().requireParentFragment() })

    private lateinit var selectedNotice: Notice

    override fun onViewCreated() {
        initView()
        with(binding) {
            toolbarNoticeEdit.setToolbarMenu("공지사항 수정", true) {
                binding.toolbarNoticeEdit.ivHamburger.setImageResource(R.drawable.baseline_edit_24)
                val title = layoutInputTitle.tilEt.text.toString()
                val content = layoutInputContent.tilEt.text.toString()
                sharedViewModel.update(
                    Notice(
                        title,
                        content,
                        Date(),
                        sharedViewModel.selectedNoticeId
                    )
                )
                backPress()
            }

            sharedViewModel.selectedNotice?.observe(
                viewLifecycleOwner
            ) { notice ->
                notice?.let {
                    selectedNotice = it
                    updateUI()
                }
            }
        }
    }

    private fun updateUI() {
        with(binding) {
            layoutInputTitle.tilEt.setText(selectedNotice.title)
            layoutInputContent.tilEt.setText(selectedNotice.content)
        }
    }

    private fun initView() {
        with(binding) {
            layoutInputTitle.tilLayout.initInFlow(
                resources.getString(R.string.notice_title),
                resources.getString(R.string.notice_helper_text)
            )

            layoutInputContent.tilLayout.initInFlow(
                resources.getString(R.string.notice_content),
                resources.getString(R.string.notice_helper_text)
            )
        }
    }
}