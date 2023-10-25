package org.sesac.management.view.event.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.sesac.management.R
import org.sesac.management.data.model.DialogItem
import org.sesac.management.databinding.FragmentArtistAddDialogBinding

class ArtistAddDialogFragment : DialogFragment() {
//    private lateinit var binding: FragmentArtistAddDialogBinding
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View {
//        binding = FragmentArtistAddDialogBinding.inflate(inflater, container, false)
//        return binding.root
//    }

    //    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding = FragmentArtistAddDialogBinding.inflate(inflater, container, false)
//        val dialog =  binding.root
//        val testItems = mutableListOf(
//            DialogItem("뉴진스", 1, false),
//            DialogItem("아이브", 2, false),
//            DialogItem("르세라핌", 3, false)
//        )
//
//        val rvArtist = binding.rvArtist
//        rvArtist.layoutManager = LinearLayoutManager(requireContext())
//        val adapter = DialogAdapter(testItems)
//        rvArtist.adapter = adapter
//
//
//        val builder = AlertDialog.Builder(requireActivity().baseContext)
//        builder.setTitle(resources.getString(R.string.event_select_artist))
//        builder.setView(dialogLayout)
//            .setPositiveButton(
//                "선택",
//                DialogInterface.OnClickListener { dialog, id ->
//
//                })
//            .setNegativeButton(
//                "닫기",
//                DialogInterface.OnClickListener { dialog, id ->
//
//                })
//
//        builder.create()
//        }
//    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = FragmentArtistAddDialogBinding.inflate(LayoutInflater.from(requireContext()))
        val dialogLayout = binding.root
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        val testItems = mutableListOf(
            DialogItem("뉴진스", 1, false),
            DialogItem("아이브", 2, false),
            DialogItem("르세라핌", 3, false)
        )

        val rvArtist = binding.rvArtist
        rvArtist.layoutManager = LinearLayoutManager(requireContext())
        val adapter = DialogAdapter(testItems)
        rvArtist.adapter = adapter


        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(resources.getString(R.string.event_select_artist))
        builder.setView(dialogLayout)
            .setPositiveButton(
                "선택",
                DialogInterface.OnClickListener { dialog, id ->

                })
            .setNegativeButton(
                "닫기",
                DialogInterface.OnClickListener { dialog, id ->

                })

        builder.create()
        return builder.create()
    }

}


/*
*/