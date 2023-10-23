package org.sesac.management.view.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.data.room.Notice
import org.sesac.management.databinding.ItemNoticeBinding

class NoticeRecyclerAdapter(
    private var items: List<Notice>,
    private val onClick: (Int) -> Unit
) :
    RecyclerView.Adapter<NoticeRecyclerAdapter.NoticeInfo>() {
    inner class NoticeInfo(val itemBinding: ItemNoticeBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {
            // 아이템 뷰 클릭 시 Fragment로 이동
            itemBinding.root.setOnClickListener {
                val position = adapterPosition
                onClick(items[absoluteAdapterPosition].noticeId)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoticeRecyclerAdapter.NoticeInfo {
        val binding =
            ItemNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoticeInfo(binding)
    }

    override fun onBindViewHolder(holder: NoticeRecyclerAdapter.NoticeInfo, position: Int) {
        val notice = items[position]
        with(holder.itemBinding) {
            tvRank.text = notice.noticeId.toString()
            tvTitle.text = notice.title
            tvDate.text = notice.createdAt.toString()
        }
    }
    override fun getItemCount(): Int = items.size

    fun setNoticeList(notices: List<Notice>) {
        items=notices
        notifyDataSetChanged()
    }
}