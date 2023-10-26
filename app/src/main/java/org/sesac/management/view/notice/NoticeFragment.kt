package org.sesac.management.view.notice

import android.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Notice
import org.sesac.management.databinding.FragmentNoticeBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.util.extension.setOnAvoidDuplicateClickFlow
import org.sesac.management.view.adapter.recyclerview.NoticeRecyclerAdapter
import org.sesac.management.view.notice.detail.NoticeDetailFragment
import org.sesac.management.view.notice.enroll.NoticeEnrollFragment

private const val TAG = "NoticeFragment"

class NoticeFragment : BaseFragment<FragmentNoticeBinding>(FragmentNoticeBinding::inflate) {

    private val noticeViewModel: NoticeViewModel by activityViewModels()

    private var adapter: NoticeRecyclerAdapter = NoticeRecyclerAdapter(emptyList(), { }) { }

    override fun onViewCreated() {
        with(binding) {
            toolbarNotice.setToolbarMenu("공지사항", true)

            btnNoticeEnrollNavigation.setOnAvoidDuplicateClick {
                noticeLayout.changeFragment(this@NoticeFragment, NoticeEnrollFragment())
            }

            rvEvent.apply {
                layoutManager =
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                observerSetup()
            }
        }
    }

    private fun observerSetup() {
        noticeViewModel.getAllNotice()?.observe(viewLifecycleOwner) { notices ->
            notices?.let {
                updateUI(notices)
            }
        }
    }

    private fun updateUI(notices: List<Notice>) {
        adapter = NoticeRecyclerAdapter(notices, onItemClick()) { noticeId ->
            AlertDialog.Builder(requireContext()).run {
                setMessage("정말로 삭제하시겠습니까?")
                setNegativeButton("cancel", null)
                setPositiveButton(
                    "ok",
                ) { _, _ ->
                    noticeViewModel.deleteNotice(noticeId)
                }
                create()
                show()
            }
        }
        binding.rvEvent.adapter = adapter
    }

    private fun onItemClick(): (Int) -> Unit = { noticeId ->
        noticeViewModel.selectedNoticeId = noticeId
        noticeViewModel.getNotice(noticeId)
        binding.noticeLayout.changeFragment(this@NoticeFragment, NoticeDetailFragment())
    }
}