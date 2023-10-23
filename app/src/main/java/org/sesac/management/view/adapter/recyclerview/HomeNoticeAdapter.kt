package org.sesac.management.view.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.data.room.Notice
import org.sesac.management.databinding.ItemHomeNoticeBinding

class HomeNoticeAdapter(val notices: List<Notice>) :
    RecyclerView.Adapter<HomeNoticeAdapter.NoticeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        return NoticeViewHolder(
            ItemHomeNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = notices.size

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.bind(notices[position])
    }

    inner class NoticeViewHolder(val binding: ItemHomeNoticeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Notice) {
            with(binding) {
                tvNoticeTitle.text = item.title
                tvNoticeContent.text = item.content
                tvNoticeDate.text = item.createdAt.toString()
            }
        }
    }
}