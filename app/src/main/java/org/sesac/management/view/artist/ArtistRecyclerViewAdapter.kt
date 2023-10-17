package org.sesac.management.view.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.R
import org.sesac.management.data.model.ArtistThumbnail
import org.sesac.management.databinding.ItemArtistEventBinding
import org.sesac.management.databinding.ItemCommonItemBinding

class ArtistRecyclerAdapter(private val items: List<ArtistThumbnail>,private val fragmentManager: FragmentManager,
    private val fragemnt: Fragment, private val fragmentContainer: Int
) :
    RecyclerView.Adapter<ArtistRecyclerAdapter.ArtistInfo>() {
    inner class ArtistInfo(val itemBinding: ItemCommonItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root){
            init {
                // 아이템 뷰 클릭 시 Fragment로 이동
                itemBinding.root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val fragment = fragemnt

                        fragmentManager.beginTransaction()
                            .add(fragmentContainer, fragment)
                            .addToBackStack(null)
                            .commit()
                    }
                }
            }
        }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtistRecyclerAdapter.ArtistInfo {
        val binding =
            ItemCommonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistInfo(binding)
    }

    override fun onBindViewHolder(holder: ArtistRecyclerAdapter.ArtistInfo, position: Int) {
        val agencyInfo = items[position]
        with(holder.itemBinding) {
            ivThumbnail.setImageResource(agencyInfo.thumbnail)
            tvTitle.text = agencyInfo.title
            tvContents.text = agencyInfo.content
        }
    }

    override fun getItemCount(): Int = items.size


}