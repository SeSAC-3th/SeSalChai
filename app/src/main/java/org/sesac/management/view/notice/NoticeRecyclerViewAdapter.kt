package org.sesac.management.view.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.R
import org.sesac.management.data.model.ArtistThumbnail
import org.sesac.management.databinding.ItemArtistEventBinding
import org.sesac.management.databinding.ItemCommonItemBinding
import org.sesac.management.databinding.ItemNoticeBinding

class NoticetRecyclerAdapter(private val items: List<ArtistThumbnail>) :
    RecyclerView.Adapter<NoticetRecyclerAdapter.NoticetInfo>() {
    inner class NoticetInfo(val itemBinding: ItemNoticeBinding) :
        RecyclerView.ViewHolder(itemBinding.root){
            init {
                // TODO: View click Listener 
            }
        }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoticetRecyclerAdapter.NoticetInfo {
        val binding =
            ItemNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoticetInfo(binding)
    }

    override fun onBindViewHolder(holder: NoticetRecyclerAdapter.NoticetInfo, position: Int) {
        val agencyInfo = items[position]
        with(holder.itemBinding) {
            tvRank.text = (position + 1).toString()
            tvTitle.text = agencyInfo.title
            tvDate.text = agencyInfo.content
        }
    }

    override fun getItemCount(): Int = items.size


}