package org.sesac.management.view.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.R
import org.sesac.management.data.model.ArtistThumbnail
import org.sesac.management.databinding.ItemArtistEventBinding
import org.sesac.management.databinding.ItemCommonItemBinding
import org.sesac.management.databinding.ItemNoticeBinding

class NoticetRecyclerAdapter(
    private val items: List<ArtistThumbnail>, private val fragmentManager: FragmentManager,
    private val fragemnt: Fragment, private val fragmentContainer: Int
) :
    RecyclerView.Adapter<NoticetRecyclerAdapter.NoticetInfo>() {
    inner class NoticetInfo(val itemBinding: ItemNoticeBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {
            // 아이템 뷰 클릭 시 Fragment로 이동
            itemBinding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val fragment = fragemnt

                    fragmentManager.beginTransaction()
                        .add(fragmentContainer, fragment)
                        .commit()
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoticetRecyclerAdapter.NoticetInfo {
        val binding =
            ItemNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoticetInfo(binding)
    }

    override fun onBindViewHolder(holder: NoticetRecyclerAdapter.NoticetInfo, position: Int) {
        val agencyInfo = items[position]
        with(holder.itemBinding) {
            tvRank.text = (position + 1).toString()
            tvTitle.text = agencyInfo.title
            tvDate.text = agencyInfo.content
        }
    }

    override fun getItemCount(): Int = items.size


}