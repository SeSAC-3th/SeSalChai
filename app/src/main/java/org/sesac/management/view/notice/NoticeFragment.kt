package org.sesac.management.view.notice

import androidx.recyclerview.widget.LinearLayoutManager
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.model.artistList
import org.sesac.management.databinding.FragmentNoticeBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.util.extension.setOnAvoidDuplicateClickFlow

class NoticeFragment : BaseFragment<FragmentNoticeBinding>(FragmentNoticeBinding::inflate) {

    override fun onViewCreated() {
        with(binding) {
            toolbarNotice.setToolbarMenu("공지사항", true)

            with(rvEvent) {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                adapter = NoticetRecyclerAdapter(
                    artistList, childFragmentManager,
                    NoticeDetailFragment(),
                    R.id.notice_layout
                )
            }

            btnNoticeEnrollNavigation.setOnAvoidDuplicateClickFlow {
                noticeLayout.changeFragment(this@NoticeFragment, NoticeEnrollFragment())
            }
        }
    }

}