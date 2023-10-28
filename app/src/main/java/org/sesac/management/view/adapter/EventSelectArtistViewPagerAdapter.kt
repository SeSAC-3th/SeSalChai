package org.sesac.management.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.R
import org.sesac.management.data.local.Artist
import org.sesac.management.databinding.ItemArtistEventBinding

/**
 * 이벤트 Detail에서 참여 아티스트 클릭시 해당 아티스트 DetailFragment로 이동합니다.
 *
 * @property items
 * @property onClick
 * @author 혜원
 */
class EventSelectArtistViewPagerAdapter(
    private val items: List<Artist>,
    private val onClick: (Int) -> Unit
) :
    RecyclerView.Adapter<EventSelectArtistViewPagerAdapter.SelectArtistViewHolder>() {
    inner class SelectArtistViewHolder(val itemBinding: ItemArtistEventBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {
            // 아이템 뷰 클릭 시 Fragment로 이동
            itemBinding.root.setOnClickListener {
                onClick(items[absoluteAdapterPosition].artistId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectArtistViewHolder {
        val binding =
            ItemArtistEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectArtistViewHolder, position: Int) {
        val artistInfo = items[position]
        with(holder.itemBinding) {
            artistInfo.imgUri?.let {
                ivEvent.setImageBitmap(it)
            } ?: {
                ivEvent.setImageResource(R.drawable.girls_generation_hyoyeon)
            }
            tvTitle.apply {
                text = artistInfo.name
            }
            tvContents.apply {
                text = artistInfo.memberInfo
            }
        }

    }

    override fun getItemCount(): Int = items.size
}