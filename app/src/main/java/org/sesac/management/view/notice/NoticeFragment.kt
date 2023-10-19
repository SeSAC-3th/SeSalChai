package org.sesac.management.view.notice

import androidx.recyclerview.widget.LinearLayoutManager
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.model.artistList
import org.sesac.management.databinding.FragmentNoticeBinding

class NoticeFragment : BaseFragment<FragmentNoticeBinding>(FragmentNoticeBinding::inflate) {

    override fun onViewCreated() {
        with(binding) {
            with(toolbarNotice) {
                tvTitle.text = "공지사항"
            }
            with(rvEvent) {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                adapter = NoticetRecyclerAdapter(
                    artistList, childFragmentManager,
                    NoticeDetailFragment(),
                    R.id.notice_layout
                )
            }

            with(btnNoticeEnrollNavigation) {
                setOnClickListener {
                    childFragmentManager
                        .beginTransaction()
                        .add(binding.noticeLayout.id, NoticeEnrollFragment())
                        .addToBackStack(null)
                        .commitAllowingStateLoss()
                }
            }
        }
    }


}