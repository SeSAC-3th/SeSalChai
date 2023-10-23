package org.sesac.management.view.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.data.model.ArtistThumbnail
import org.sesac.management.databinding.ItemEventEnrollAddArtistBinding

class ArtistAddRecyclerViewAdapter(val artists: List<ArtistThumbnail>) :
    RecyclerView.Adapter<ArtistAddRecyclerViewAdapter.ArtistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        return ArtistViewHolder(
            ItemEventEnrollAddArtistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = artists.size

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(artists[position])
    }


    inner class ArtistViewHolder(private val binding: ItemEventEnrollAddArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArtistThumbnail) {
            with(binding) {
                tvArtistName.text = item.title
            }
        }
    }
}