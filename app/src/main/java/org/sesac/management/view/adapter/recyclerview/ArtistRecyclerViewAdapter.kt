package org.sesac.management.view.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.R
import org.sesac.management.data.local.Artist
import org.sesac.management.data.model.ArtistThumbnail
import org.sesac.management.databinding.ItemCommonItemBinding

class ArtistRecyclerAdapter(
    private val items: List<Artist>, private val onClick: () -> Unit
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
    ): ArtistInfo {
        val binding =
            ItemCommonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistInfo(binding)
    }

    override fun onBindViewHolder(holder: ArtistInfo, position: Int) {
        val agencyInfo = items[position]
        with(holder.itemBinding) {
            ivThumbnail.setImageResource(R.drawable.girls_generation_hyoyeon)
            tvTitle.text = agencyInfo.name
            tvContents.text = agencyInfo.type.toString()
        }
    }

    override fun getItemCount(): Int = items.size


}