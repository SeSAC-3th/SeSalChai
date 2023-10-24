package org.sesac.management.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.databinding.ItemRateRankingBinding

class RateAdapter(
    private val rankingList: MutableList<String>, private val onClick: (String) -> Unit,
) : RecyclerView.Adapter<RateAdapter.RateViewHolder>() {

    inner class RateViewHolder(val binding: ItemRateRankingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(temp: String) {
            binding.circleIVArtistPicture.setImageResource(org.sesac.management.R.drawable.ic_launcher_background)
            binding.tvRanking.text = "${absoluteAdapterPosition + 1}"
            binding.tvArtistName.text = rankingList[absoluteAdapterPosition]
            binding.layoutItemRateRanking.setOnClickListener {
                onClick(rankingList[absoluteAdapterPosition])
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