package org.sesac.management.view.notice.edit

import android.util.Log
import androidx.fragment.app.viewModels
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Notice
import org.sesac.management.databinding.FragmentNoticeEditBinding
import org.sesac.management.view.notice.NoticeViewModel
import java.util.Date

class NoticeEditFragment
    : BaseFragment<FragmentNoticeEditBinding>(FragmentNoticeEditBinding::inflate) {

    private val sharedViewModel: NoticeViewModel
            by viewModels(ownerProducer = { requireParentFragment().requireParentFragment() })

    private lateinit var selectedNotice: Notice

    override fun onViewCreated() {
        Log.e("aaaa", "${sharedViewModel.selectedNoticeId}")
        with(binding) {
            toolbarNoticeEdit.setToolbarMenu("공지사항 수정", true)

            sharedViewModel.selectedNotice?.observe(
                viewLifecycleOwner
            ) { notice ->
                notice?.let {
                    selectedNotice = it
                    updateUI()
                }
            }

            btnSave.setOnAvoidDuplicateClick {
                val title = etTitle.text.toString()
                val content = etContent.text.toString()
                sharedViewModel.update(
                    Notice(
                        title,
                        content,
                        Date(),
                        sharedViewModel.selectedNoticeId
                    )
                )
            }
        }
    }

    private fun updateUI() {
        with(binding) {
            etTitle.setText(selectedNotice.title)
            etContent.setText(selectedNotice.content)
        }
    }
}