package org.sesac.management.view.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.R
import org.sesac.management.data.local.Artist
import org.sesac.management.databinding.ItemRateRankingBinding

class RateAdapter(
    private val rankingList: List<Artist>, private val onClick: (Artist) -> Unit,
) : RecyclerView.Adapter<RateAdapter.RateViewHolder>() {

    inner class RateViewHolder(val binding: ItemRateRankingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(temp: Artist) {
            with(binding) {
                tvRanking.text = "${absoluteAdapterPosition + 1}"

                tvArtistName.text = temp.name

                if (temp.imgUri != null) {
                    circleIVArtistPicture.setImageBitmap(temp.imgUri)
                } else {
                    circleIVArtistPicture.setImageResource(R.drawable.girls_generation_hyoyeon)
                }

                layoutItemRateRanking.setOnClickListener {
                    onClick(temp)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val binding =
            ItemRateRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.bind(rankingList[position])
    }

    override fun getItemCount() = rankingList.size
}