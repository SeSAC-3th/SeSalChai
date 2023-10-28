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

            // 선택된 공지 사항 관찰
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

    /**
     * Update ui
     * 이전 상세 내용 에서 선택된 공지 사항의 내용을 채우는 메서드
     */
    private fun updateUI() {
        with(binding) {
            layoutInputTitle.tilEt.setText(selectedNotice.title)
            layoutInputContent.tilEt.setText(selectedNotice.content)
        }
    }

    /**
     * InitView
     * textInput layout의 가이드를 설정 하는 메서드
     */
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

    /**
     * Check input
     * 공지 사항의 제목과 내용을 모두  입력 했는지 확인 하는 메서드
     * @return : 모두 입력 했을 때 false 값 반환
     */
    private fun checkInput(): Boolean {
        val flag = binding.layoutInputTitle.tilEt.text.toString().isEmpty() ||
                binding.layoutInputContent.tilEt.text.toString().isEmpty()
        if (flag) {
            message = "제목, 내용을 모두 입력해주세요"
        }
        return flag
    }
}