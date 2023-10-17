package org.sesac.management.view.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.R
import org.sesac.management.data.model.ArtistThumbnail
import org.sesac.management.databinding.ItemHomeArtistBinding

class EventRecyclerViewAdapter(val artistList: MutableList<ArtistThumbnail>) :
    RecyclerView.Adapter<EventRecyclerViewAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            ItemHomeArtistBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = artistList.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val artist = artistList[position]
        holder.bind(artist)
    }


    inner class EventViewHolder(private val binding: ItemHomeArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArtistThumbnail) {
            with(binding) {
                binding.mcvArtistImage.setImageResource(R.drawable.girls_generation_all)
            }
        }
    }
}