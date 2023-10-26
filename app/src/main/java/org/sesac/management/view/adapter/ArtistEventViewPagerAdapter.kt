package org.sesac.management.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.R
import org.sesac.management.data.local.Event
import org.sesac.management.databinding.ItemArtistEventBinding

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
            ivEvent.apply {
                setImageResource((eventInfo.imgUri ?: R.drawable.ic_launcher_background) as Int)
            }
            tvTitle.apply {
                text = eventInfo.name
            }
        }

    }

    override fun getItemCount(): Int = items.size

//    class EventViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
//        fun bind(event: Event) {
//            view.findViewById<ImageView>(R.id.iv_event).apply {
//                setImageResource((event.imgUri ?: R.drawable.ic_launcher_background) as Int)
//            }
//            view.findViewById<TextView>(R.id.tv_title).apply {
//                text = event.name
//            }
//        }
//    }
}