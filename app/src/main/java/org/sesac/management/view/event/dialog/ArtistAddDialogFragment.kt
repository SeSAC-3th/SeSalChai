package org.sesac.management.view.event.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.sesac.management.R
import org.sesac.management.data.model.DialogItem
import org.sesac.management.databinding.FragmentArtistAddDialogBinding
import org.sesac.management.view.artist.ArtistViewModel

class ArtistAddDialogFragment : DialogFragment() {
    private val viewModel: ArtistViewModel by activityViewModels()
    private val selectedItems = mutableListOf<String>()
    val TAG: String = "로그"
    private lateinit var binding: FragmentArtistAddDialogBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.binding = FragmentArtistAddDialogBinding.inflate(LayoutInflater.from(requireContext()))
        /**
         * 전체 아티스트 viewModel에서 가져오기
         */
        viewModel.getAllArtist.observe(viewLifecycleOwner) { artist ->
            // 아티스트 목록을 가져온 후 RecyclerView에 표시
            Log.d(TAG, "getAllArtist : $artist")
            val rvArtist = this.binding.rvArtist
            rvArtist.layoutManager = LinearLayoutManager(requireContext())
            // artists를 DialogItem으로 변환
            val dialogItems = artist.map { artist ->
                DialogItem(artist.name, artist.artistId, selectedItems.contains(artist.name))
            }
            val adapter = DialogAdapter(dialogItems)
            rvArtist.adapter = adapter
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        this.binding = FragmentArtistAddDialogBinding.inflate(LayoutInflater.from(requireContext()))
        val dialogLayout = this.binding.root

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            var co = viewModel.getAllArtist.value
            // 아티스트 목록을 가져온 후 RecyclerView에 표시
            Log.d(TAG, "getAllArtist : ${co}")
            val rvArtist = this.binding.rvArtist
            rvArtist.layoutManager = LinearLayoutManager(requireContext())
            // artists를 DialogItem으로 변환
            val dialogItems = co?.map { artist ->
                DialogItem(artist.name, artist.artistId, selectedItems.contains(artist.name))
            }
            val adapter = dialogItems?.let { DialogAdapter(it) }
            rvArtist.adapter = adapter

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(resources.getString(R.string.event_select_artist))
        builder.setView(dialogLayout)
            .setPositiveButton(
                "선택",
                DialogInterface.OnClickListener { dialog, id ->
                    // 선택된 아티스트 List 형태로 만들기
                    val selectedArtists = selectedItems.toList()
                    // 콜백 실행
                    val listener = activity as CustomDialogListener
                    listener.onArtistsSelected(selectedArtists)

                    // Dialog 닫기
                    dismiss()
                })
            .setNegativeButton(
                "닫기",
                DialogInterface.OnClickListener { dialog, id ->
                    // 다이얼로그를 닫는 작업
                })

        builder.create()
        return builder.create()
    }
}

/**
 * Dialog Callback을 위한 interface
 * @author 혜원
 */
