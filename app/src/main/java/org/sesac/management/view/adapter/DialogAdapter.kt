package org.sesac.management.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.data.model.DialogItem
import org.sesac.management.databinding.ItemEventEnrollAddArtistBinding
import org.sesac.management.view.event.dialog.CustomDialogListener

/**
 * Dialog adapter
 *
 * @property itemList
 * @property customDialogListener
 * @constructor Create empty Dialog adapter
 * @author 혜원
 */
class DialogAdapter(private val itemList: List<DialogItem>, private val customDialogListener: CustomDialogListener) : RecyclerView.Adapter<DialogAdapter.DialogViewHolder>() {
    private val checkedItems = mutableListOf<Boolean>()
    val TAG: String = "로그"
    inner class DialogViewHolder(val binding: ItemEventEnrollAddArtistBinding) : RecyclerView.ViewHolder(binding.root) {
        val checkBox: CheckBox = binding.cbArtist
        init {
            // 체크박스 상태 변경 시 리스너 등록
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                checkedItems[adapterPosition] = isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogViewHolder {
        val binding = ItemEventEnrollAddArtistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DialogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DialogViewHolder, position: Int) {
        val item = itemList[position]
        val binding = holder.binding

        with(binding) {
            tvArtistName.text = item.artistName
            cbArtist.isChecked = item.isChecked
            // 체크박스 상태 변경 시, 데이터 모델 업데이트
            cbArtist.setOnCheckedChangeListener { _, isChecked ->
                item.isChecked = isChecked
                customDialogListener.onItemSelected(item.artistName, item.artistId, item.isChecked)
            }
        }
    }
    override fun getItemCount() = itemList.size
}

