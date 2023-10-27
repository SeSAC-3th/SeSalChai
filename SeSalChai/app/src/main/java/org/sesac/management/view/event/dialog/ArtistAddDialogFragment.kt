package org.sesac.management.view.event.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.R
import org.sesac.management.data.model.DialogItem
import org.sesac.management.databinding.FragmentArtistAddDialogBinding
import org.sesac.management.view.adapter.DialogAdapter
import org.sesac.management.view.artist.ArtistViewModel

/**
 * Artist add dialog fragment
 *
 * @constructor Create empty Artist add dialog fragment
 * @author 혜원
 */
class ArtistAddDialogFragment : DialogFragment(), CustomDialogListener {
    private val viewModel: ArtistViewModel by activityViewModels()
    private val selectedItems = mutableListOf<String>()
    private val dialogCheck = mutableListOf<DialogItem>()
    private var dialogDataListener: DialogDataListener? = null

    val TAG: String = "로그"
    private lateinit var binding: FragmentArtistAddDialogBinding

    /**
     * DialogFragment를 Custom하여 구현
     * setView 영역에 RecyclerView를 연결하여
     * 전체 아티스트 목록을 출력할 수 있도록 구현하였습니다.
     * CheckBox 클릭시, interface를 통해 EventEnroll 또는 EventEdit으로
     * 선택한 아티스트의 데이터 값을 넘겨줍니다.
     * @author 혜원
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        this.binding = FragmentArtistAddDialogBinding.inflate(LayoutInflater.from(requireContext()))
        val dialogLayout = this.binding.root

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        val allArtist = viewModel.getAllArtist.value
        // 아티스트 목록을 가져온 후 RecyclerView에 표시
        val rvArtist = dialogLayout.findViewById<RecyclerView>(R.id.rv_artist)
        rvArtist.layoutManager = LinearLayoutManager(requireContext())
        // artists를 DialogItem으로 변환
        val dialogItems = allArtist?.map { artist ->
            DialogItem(artist.name , artist.artistId, selectedItems.contains(artist.name))
        }

        val adapter = dialogItems?.let { DialogAdapter(it, this) }
        rvArtist.adapter = adapter

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(resources.getString(R.string.event_select_artist))
        builder.setView(dialogLayout)
            .setPositiveButton(
                "선택"
            ) { dialog, id ->
                sendDataToPreviousFragment(dialogCheck)
                dismiss()
            }
            .setNegativeButton(
                "닫기",
                DialogInterface.OnClickListener { dialog, id ->
                    dismiss()
                })

        builder.create()
        return builder.create()
    }

    /**
     * Check한 item을 등록시, DIalogDataSelected로 데이터를 넘긴 후
     * Dialog를 닫는다.
     *
     * @author 혜원
     */
    fun onDialogDataSelected(listener: DialogDataListener) {
        dialogDataListener = listener
    }

    // "확인" 버튼을 클릭했을 때 데이터 전송
    private fun sendDataToPreviousFragment(sendData: MutableList<DialogItem>) {
        dialogDataListener?.onDialogDataSelected(sendData)
        dismiss() // Dialog 닫기
    }

    /**
     * RecyclerView에서 Check한 item을 List에 추가 및 삭제하기
     *
     * @param artistName
     * @param artistId
     * @param isChecked
     * @author 혜원
     */
    override fun onItemSelected(artistName: String, artistId: Int, isChecked: Boolean) {
        // 체크박스 상태가 변경된 경우, 이 메서드가 호출됩니다.
        if (isChecked) {
            dialogCheck.add(DialogItem(artistName, artistId, isChecked))
        } else {
            // 선택이 해제된 아이템의 처리
            dialogCheck.remove(DialogItem(artistName, artistId, isChecked))
        }
    }

}