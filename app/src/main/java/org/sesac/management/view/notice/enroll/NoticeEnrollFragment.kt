package org.sesac.management.view.notice.enroll

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Notice
import org.sesac.management.databinding.FragmentNoticeEnrollBinding
import org.sesac.management.util.common.showToastMessage
import org.sesac.management.util.extension.afterTextChangesInFlow
import org.sesac.management.util.extension.focusChangesInFlow
import org.sesac.management.util.extension.initInFlow
import org.sesac.management.view.notice.NoticeViewModel
import reactivecircus.flowbinding.android.widget.AfterTextChangeEvent
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale

class NoticeEnrollFragment() :
    BaseFragment<FragmentNoticeEnrollBinding>(FragmentNoticeEnrollBinding::inflate) {

    private val noticeViewModel: NoticeViewModel by activityViewModels()

    private var message: String = ""
    override fun onViewCreated() {
        initView()
        with(binding) {
            toolbarNoticeEnroll.setToolbarMenu("공지사항 등록", true) {
                binding.toolbarNoticeEnroll.ivHamburger.setImageResource(R.drawable.baseline_edit_24)

                if (checkInput()) {
                    showToastMessage(message)
                } else {
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
    }

    /**
     * Initview
     * TextInput Layout의 가이드를 위한 메서드
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
     * CheckInput
     * 제목과 내용을 모두 입력 했는지 확인 하는 메서드
     * @return : 모두 입력 했을 시 false 반환
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