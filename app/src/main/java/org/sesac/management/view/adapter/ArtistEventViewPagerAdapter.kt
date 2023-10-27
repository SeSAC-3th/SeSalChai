package org.sesac.management.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.data.local.Event
import org.sesac.management.databinding.ItemArtistEventBinding

/**
 * Artist event view pager adapter
 *
 * @property items
 * @property onClick
 * @constructor Create empty Artist event view pager adapter
 * @author 혜원
 */
class ArtistEventViewPagerAdapter(
    private val items: List<Event>,
    private val onClick: (Int) -> Unit
) :
    RecyclerView.Adapter<ArtistEventViewPagerAdapter.EventViewHolder>() {
    inner class EventViewHolder(val itemBinding: ItemArtistEventBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {
            // 아이템 뷰 클릭 시 Fragment로 이동
            itemBinding.root.setOnClickListener {
                onClick(items[absoluteAdapterPosition].eventId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding =
            ItemArtistEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val eventInfo = items[position]
        with(holder.itemBinding) {
            eventInfo.imgUri?.let {
                ivEvent.setImageBitmap(it)
            }
            tvTitle.apply {
                text = eventInfo.name
            }
        }

    }

    override fun getItemCount(): Int = items.size
}