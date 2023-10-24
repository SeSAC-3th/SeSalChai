package org.sesac.management.view.notice.detail

import android.util.Log
import androidx.fragment.app.viewModels
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Notice
import org.sesac.management.databinding.FragmentNoticeDetailBinding
import org.sesac.management.view.notice.NoticeViewModel

class NoticeDetailFragment() :
    BaseFragment<FragmentNoticeDetailBinding>(FragmentNoticeDetailBinding::inflate) {
    private val viewModel: NoticeViewModel by viewModels()
    var notice: Notice? = null
    override fun onViewCreated() {
//        val id=arguments?.getInt("notice_id",0) ?: 0
        viewModel.getNotice(id)
        with(binding) {
            toolbarNoticeDetail.setToolbarMenu("공지사항 상세", true)
        }

        viewModel.selectedNotice?.observe(
            viewLifecycleOwner
        ) { notice ->
            notice?.let {
                Log.e("asdf", "$notice")
                this.notice = notice
                updateUi()
            }
        }
    }

    fun updateUi() {
        with(binding) {
            tvNoticeTitle.text = notice?.title.toString()
            tvContent.text = notice?.content
            tvDate.text = notice?.createdAt.toString()
        }
    }

}