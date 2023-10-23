package org.sesac.management.view.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.data.room.Artist
import org.sesac.management.databinding.ItemHomeArtistBinding

class HomeArtistAdapter(val artists: List<Artist>) :
    RecyclerView.Adapter<HomeArtistAdapter.ArtistViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtistViewHolder {
        return ArtistViewHolder(
            ItemHomeArtistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(artists[position])
    }

    override fun getItemCount() = artists.size


    inner class ArtistViewHolder(val binding: ItemHomeArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Artist) {
            with(binding) {
                tvArtistName.text = item.name
            }
        }
    }
}