package org.sesac.management.view.notice.enroll

import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Notice
import org.sesac.management.databinding.FragmentNoticeEnrollBinding
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

    private val noticeViewModel: NoticeViewModel by viewModels()
    override fun onViewCreated() {
        initView()
        initTextWatcher()
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

    private fun initTextWatcher() {
        with(binding) {
            layoutInputTitle.tilLayout.afterTextChangesInFlow(inputTitle)
            layoutInputTitle.tilLayout.focusChangesInFlow(focus)

            layoutInputContent.tilLayout.afterTextChangesInFlow(inputContent)
            layoutInputTitle.tilLayout.focusChangesInFlow(focus)
        }
    }

    private val inputTitle = { layout: TextInputLayout, title: AfterTextChangeEvent ->
        val inputTitle = title.editable.toString()
        if (inputTitle.isEmpty()) {
            layout.initInFlow(
                resources.getString(R.string.notice_title),
                resources.getString(R.string.notice_error_title_empty),
            )
        }
    }

    private val inputContent = { layout: TextInputLayout, title: AfterTextChangeEvent ->
        val inputContent = title.editable.toString()
        if (inputContent.isEmpty()) {
            layout.initInFlow(
                resources.getString(R.string.notice_content),
                resources.getString(R.string.notice_error_content_empty),
            )
        }
    }

    private val focus = { layout: TextInputLayout, hasFocus: Boolean ->
        if (hasFocus) layout.error = null
    }

}