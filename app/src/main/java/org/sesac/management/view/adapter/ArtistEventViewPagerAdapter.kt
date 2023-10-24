package org.sesac.management.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.R

class ArtistEventViewPagerAdapter(private val items: List<Int>) :
    RecyclerView.Adapter<ArtistEventViewPagerAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_artist_event, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class EventViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(event: Int) {
            view.findViewById<ImageView>(R.id.iv_event).apply {
                setImageResource(R.drawable.ic_launcher_background)
            }
        }
    }
}