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
    private val onDeleteClick: (Int) -> Unit,
) :
    RecyclerView.Adapter<NoticeRecyclerAdapter.NoticeInfo>() {
    inner class NoticeInfo(val itemBinding: ItemNoticeBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: Notice) {
            with(itemBinding) {
                tvRank.text = item.noticeId.toString()
                tvTitle.text = item.title
                tvDate.text = item.createdAt.toString()

                root.setOnAvoidDuplicateClickFlow {
                    onClick(item.noticeId)
                }

                ivDeleteNotice.setOnAvoidDuplicateClickFlow {
                    onDeleteClick(item.noticeId)
                }
            }
        }
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
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}