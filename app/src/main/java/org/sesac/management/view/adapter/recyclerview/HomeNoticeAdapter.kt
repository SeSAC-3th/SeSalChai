package org.sesac.management.view.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.data.local.Notice
import org.sesac.management.databinding.ItemHomeNoticeBinding
import java.text.SimpleDateFormat
import java.util.Locale

class HomeNoticeAdapter(private val notices: List<Notice>) :
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
                tvNoticeDate.text = SimpleDateFormat(
                    "yyyy년 MMM dd일", Locale.KOREA
                ).format(item.createdAt)
            }
        }
    }
}