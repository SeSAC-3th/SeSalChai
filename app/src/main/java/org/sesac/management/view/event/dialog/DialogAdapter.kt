package org.sesac.management.view.event.dialog

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.data.model.DialogItem
import org.sesac.management.databinding.ItemEventEnrollAddArtistBinding

class DialogAdapter(private val itemList: List<DialogItem>) :
    RecyclerView.Adapter<DialogAdapter.DialogViewHolder>() {
    val TAG: String = "로그"

    class DialogViewHolder(val binding: ItemEventEnrollAddArtistBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogViewHolder {
        val binding = ItemEventEnrollAddArtistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DialogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DialogViewHolder, position: Int) {
        val item = itemList[position]
        Log.d(TAG, " RecyclerView item : $item")
        val binding = holder.binding

        with(binding) {
            tvArtistName.text = item.artistName
            cbArtist.isChecked = item.isChecked
            // 체크박스 상태 변경 시, 데이터 모델 업데이트
            binding.cbArtist.setOnCheckedChangeListener { _, isChecked ->
                item.isChecked = isChecked
            }
        }
    }
    override fun getItemCount() = itemList.size
}

