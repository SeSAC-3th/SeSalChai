package org.sesac.management.view.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.data.model.ArtistThumbnail
import org.sesac.management.databinding.ItemCommonItemBinding

class ArtistRecyclerAdapter(
    private val items: List<ArtistThumbnail>, private val onClick: () -> Unit
) :
    RecyclerView.Adapter<ArtistRecyclerAdapter.ArtistInfo>() {
    inner class ArtistInfo(val itemBinding: ItemCommonItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {
            // 아이템 뷰 클릭 시 Fragment로 이동
            itemBinding.root.setOnClickListener {
                onClick()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtistRecyclerAdapter.ArtistInfo {
        val binding =
            ItemCommonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistInfo(binding)
    }

    override fun onBindViewHolder(holder: ArtistRecyclerAdapter.ArtistInfo, position: Int) {
        val agencyInfo = items[position]
        with(holder.itemBinding) {
            ivThumbnail.setImageResource(agencyInfo.thumbnail)
            tvTitle.text = agencyInfo.title
            tvContents.text = agencyInfo.content
        }
    }

    override fun getItemCount(): Int = items.size


}