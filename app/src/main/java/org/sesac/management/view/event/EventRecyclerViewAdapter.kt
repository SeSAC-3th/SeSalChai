package org.sesac.management.view.event

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.R
import org.sesac.management.data.model.ArtistThumbnail
import org.sesac.management.data.model.artistList
import org.sesac.management.databinding.ItemEventDetailArtistBinding
import org.sesac.management.databinding.ItemHomeArtistBinding

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