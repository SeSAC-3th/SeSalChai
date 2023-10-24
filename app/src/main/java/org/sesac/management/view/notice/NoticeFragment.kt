package org.sesac.management.view.notice

import android.app.AlertDialog
import android.app.ProgressDialog.show
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.core.view.children
import androidx.fragment.app.Fragment
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
private const val TAG="NoticeFragment"
class NoticeFragment : BaseFragment<FragmentNoticeBinding>(FragmentNoticeBinding::inflate) {
    private val viewModel: NoticeViewModel by viewModels()
    private var adapter: NoticeRecyclerAdapter = NoticeRecyclerAdapter(emptyList(), { }) { }

    override fun onViewCreated() {
        with(binding) {
            toolbarNotice.setToolbarMenu("공지사항", true)
            btnNoticeEnrollNavigation.setOnAvoidDuplicateClick {
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
        adapter = NoticeRecyclerAdapter(notices, onItemClick()) { noticeId ->
            AlertDialog.Builder(requireContext()).run {
                setTitle("삭제")
                setMessage("정말로 삭제하시겠습니까?")
                setNegativeButton("cancel", null)
                setPositiveButton("ok",
                    DialogInterface.OnClickListener { _, _ ->
                        viewModel.deleteNotice(noticeId)
                    }
                )
                create()
                show()
            }
        }
        binding.rvEvent.adapter = adapter
    }

    private fun onItemClick(): (Int) -> Unit = { noticeId ->
        val bundle = Bundle()
        bundle.putInt("notice_id", noticeId)
        val fragment = NoticeDetailFragment()
        fragment.arguments = bundle
        childFragmentManager.beginTransaction()
            .replace(binding.noticeLayout.id, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    override fun onDetach() {
        super.onDetach()
        Log.e(TAG,"onDetach( )")
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG,"onPause( )")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG,"onDestroy( )")
    }
}