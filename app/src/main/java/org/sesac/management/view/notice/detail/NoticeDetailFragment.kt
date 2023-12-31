package org.sesac.management.view.notice.detail

import androidx.fragment.app.activityViewModels
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Notice
import org.sesac.management.databinding.FragmentNoticeDetailBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.view.notice.NoticeViewModel
import org.sesac.management.view.notice.edit.NoticeEditFragment
import java.text.SimpleDateFormat
import java.util.Locale

class NoticeDetailFragment() :
    BaseFragment<FragmentNoticeDetailBinding>(FragmentNoticeDetailBinding::inflate) {
    private val noticeViewModel: NoticeViewModel by activityViewModels()

    override fun onViewCreated() {
        with(binding) {
            toolbarNoticeDetail.setToolbarMenu("공지사항 상세", true) {
                binding.layoutNoticeDetail.changeFragment(
                    this@NoticeDetailFragment,
                    NoticeEditFragment()
                )
            }
        }

        // LiveData 관찰
        noticeViewModel.selectedNotice?.observe(
            viewLifecycleOwner
        ) { notice ->
            notice?.let {
                updateUI(it)
            }
        }
    }

    /**
     * Update ui
     * LiveData로 관찰된 공지 사항의 내용을 띄우는 메서드
     * @param notice : 선택된 공지 사항
     */
    private fun updateUI(notice: Notice) {
        with(binding) {
            tvNoticeTitle.text = notice.title
            tvContent.text = notice.content
            tvDate.text = SimpleDateFormat(
                "yyyy년 MMM dd일 ", Locale.KOREA
            ).format(notice.createdAt)
        }
    }
}