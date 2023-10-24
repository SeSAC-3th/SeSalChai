package org.sesac.management.view.notice

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Notice
import org.sesac.management.databinding.FragmentNoticeBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.util.extension.setOnAvoidDuplicateClickFlow
import org.sesac.management.view.adapter.recyclerview.NoticeRecyclerAdapter
import org.sesac.management.view.notice.detail.NoticeDetailFragment
import org.sesac.management.view.notice.enroll.NoticeEnrollFragment

class NoticeFragment : BaseFragment<FragmentNoticeBinding>(FragmentNoticeBinding::inflate) {
    private val viewModel: NoticeViewModel by viewModels()
    private var adapter: NoticeRecyclerAdapter = NoticeRecyclerAdapter(emptyList()) { }

    override fun onViewCreated() {
        with(binding) {
            toolbarNotice.setToolbarMenu("공지사항", true)
            btnNoticeEnrollNavigation.setOnAvoidDuplicateClickFlow {
                noticeLayout.changeFragment(this@NoticeFragment, NoticeEnrollFragment())
            }

            rvEvent.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                observerSetup()
            }
        }
    }

    private fun observerSetup() {
        viewModel.getAllNotice()?.observe(viewLifecycleOwner) { notices ->
            notices?.let {
                updateUI(notices)
            }
        }
    }

    private fun updateUI(notices: List<Notice>) {
        adapter = NoticeRecyclerAdapter(notices) { noticeId ->
//            val bundle=Bundle()
//            bundle.putInt("notice_id",noticeId)
//            val fragment= NoticeDetailFragment()
//            fragment.arguments=bundle
            viewModel.getNotice(noticeId)
            binding.noticeLayout.changeFragment(this@NoticeFragment, NoticeDetailFragment())
        }
        binding.rvEvent.adapter = adapter
    }
}