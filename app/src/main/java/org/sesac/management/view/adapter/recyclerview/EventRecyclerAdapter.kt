package org.sesac.management.view.adapter.recyclerview

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.R
import org.sesac.management.data.local.Event
import org.sesac.management.databinding.ItemCommonItemBinding
import org.sesac.management.util.common.ApplicationClass.Companion.getApplicationContext

class EventRecyclerAdapter(
    private val items: List<Event>, private val onClick: (Int) -> Unit,
    private val onDelete: (Event) -> Unit
) :
    RecyclerView.Adapter<EventRecyclerAdapter.EventInfo>() {
    inner class EventInfo(val itemBinding: ItemCommonItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {
            // 아이템 뷰 클릭 시 Fragment로 이동
            itemBinding.root.setOnClickListener {
                onClick(items[absoluteAdapterPosition].eventId)
            }
            itemBinding.btnDelete.setOnClickListener {
                Toast.makeText(getApplicationContext(),"데이터가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                onDelete(items[absoluteAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventInfo {
        val binding =
            ItemCommonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventInfo(binding)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: EventInfo, position: Int) {
        val agencyInfo = items[position]
        val date = SimpleDateFormat("yyyy-MM-dd").format(agencyInfo.date)

        with(holder.itemBinding) {
            agencyInfo.imgUri?.let {
                ivThumbnail.setImageBitmap(it)
            } ?: {
                ivThumbnail.setImageResource(R.drawable.girls_generation_hyoyeon)
            }
            tvTitle.text = agencyInfo.name
            tvContents.text = date
        }
    }

    override fun getItemCount(): Int = items.size
}