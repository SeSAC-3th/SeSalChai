package org.sesac.management.view.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.data.local.Notice
import org.sesac.management.databinding.ItemNoticeBinding
import org.sesac.management.util.extension.setOnAvoidDuplicateClickFlow

class NoticeRecyclerAdapter(
    private var items: List<Notice>,
    private val onClick: (Int) -> Unit,
    private val onDeleteClick: (Int)->Unit,
) :
    RecyclerView.Adapter<NoticeRecyclerAdapter.NoticeInfo>() {
    inner class NoticeInfo(val itemBinding: ItemNoticeBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoticeInfo {
        val binding =
            ItemNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoticeInfo(binding)
    }

    override fun onBindViewHolder(holder: NoticeInfo, position: Int) {
        val notice = items[position]
        with(holder.itemBinding) {
            tvRank.text = notice.noticeId.toString()
            tvTitle.text = notice.title
            tvDate.text = notice.createdAt.toString()

            root.setOnAvoidDuplicateClickFlow {
                onClick(notice.noticeId)
            }

            ivDeleteNotice.setOnAvoidDuplicateClickFlow {
                onDeleteClick(notice.noticeId)
            }
        }
    }

    override fun getItemCount(): Int = items.size

}