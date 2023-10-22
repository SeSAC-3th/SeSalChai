package org.sesac.management.view.notice

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.model.artistList
import org.sesac.management.data.room.Notice
import org.sesac.management.databinding.FragmentNoticeBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.util.extension.setOnAvoidDuplicateClickFlow
import java.util.Date

class NoticeFragment : BaseFragment<FragmentNoticeBinding>(FragmentNoticeBinding::inflate) {
    private val viewModel: NoticeViewModel by viewModels()

    override fun onViewCreated() {
        lifecycleScope.launch {
            viewModel.insertMemberInfo(Notice(title = "제목 1", content = "내용 1", createdAt = Date()))
        }
        with(binding) {
            toolbarNotice.setToolbarMenu("공지사항", true)

            with(rvEvent) {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                adapter = NoticetRecyclerAdapter(
                    artistList,
                    onClick = {
                        binding.noticeLayout.changeFragment(
                            this@NoticeFragment, NoticeDetailFragment()
                        )
                    }
                )
            }

            btnNoticeEnrollNavigation.setOnAvoidDuplicateClickFlow {
                noticeLayout.changeFragment(this@NoticeFragment, NoticeEnrollFragment())
            }
        }
    }


}