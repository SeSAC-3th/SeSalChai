package org.sesac.management.view.notice.edit

import androidx.fragment.app.activityViewModels
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Notice
import org.sesac.management.databinding.FragmentNoticeEditBinding
import org.sesac.management.util.common.showToastMessage
import org.sesac.management.util.extension.initInFlow
import org.sesac.management.view.notice.NoticeViewModel
import java.util.Date

class NoticeEditFragment
    : BaseFragment<FragmentNoticeEditBinding>(FragmentNoticeEditBinding::inflate) {

    private val noticeViewModel: NoticeViewModel by activityViewModels()

    private lateinit var selectedNotice: Notice

    private var message: String = ""

    override fun onViewCreated() {
        initView()
        with(binding) {
            toolbarNoticeEdit.setToolbarMenu("공지사항 수정", true) {
                binding.toolbarNoticeEdit.ivHamburger.setImageResource(R.drawable.baseline_edit_24)

                if (checkInput()) {
                    showToastMessage(message)
                } else {
                    val title = layoutInputTitle.tilEt.text.toString()
                    val content = layoutInputContent.tilEt.text.toString()
                    noticeViewModel.update(
                        Notice(
                            title,
                            content,
                            Date(),
                            noticeViewModel.selectedNoticeId
                        )
                    )
                    backPress()
                }
            }

            noticeViewModel.selectedNotice?.observe(
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
                resources.getString(R.string.notice_error_title_empty)
            )

            layoutInputContent.tilLayout.initInFlow(
                resources.getString(R.string.notice_content),
                resources.getString(R.string.notice_error_content_empty)
            )
        }
    }

    private fun checkInput(): Boolean {
        val flag = binding.layoutInputTitle.tilEt.text.toString().isEmpty() ||
                binding.layoutInputContent.tilEt.text.toString().isEmpty()
        if (flag) {
            message = "제목, 내용을 모두 입력해주세요"
        }
        return flag
    }
}