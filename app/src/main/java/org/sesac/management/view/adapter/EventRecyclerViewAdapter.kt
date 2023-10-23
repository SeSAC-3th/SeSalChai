package org.sesac.management.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.data.model.ArtistThumbnail
import org.sesac.management.databinding.ItemEventDetailArtistBinding

class EventRecyclerViewAdapter(val artistList: List<ArtistThumbnail>) :
    RecyclerView.Adapter<EventRecyclerViewAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            ItemEventDetailArtistBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = artistList.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val artist = artistList[position]
        holder.bind(artist)
    }

    inner class EventViewHolder(private val binding: ItemEventDetailArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArtistThumbnail) {
            with(binding) {
                ivEvent.setImageResource(item.thumbnail)
                tvTitle.text = item.title
            }
        }
    }
}