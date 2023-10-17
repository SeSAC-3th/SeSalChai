package org.sesac.management.view.rate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.R
import org.sesac.management.databinding.ItemRateRankingBinding

class RateAdapter(
    private val rankingList: MutableList<String>,
) : RecyclerView.Adapter<RateAdapter.RateViewHolder>() {

    inner class RateViewHolder(val binding: ItemRateRankingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val binding =
            ItemRateRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RateViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        val rankingData = rankingList[position]
        with(holder.binding) {
            circleIVArtistPicture.setImageResource(R.drawable.ic_launcher_background)
            tvRanking.text = "${position + 1}"
            tvArtistName.text = rankingData
        }
    }

    override fun getItemCount() = rankingList.size
}