package org.sesac.management.view.event

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentArtistAddDialogBinding

class ArtistAddDialogFragment : DialogFragment(){
    private lateinit var binding : FragmentArtistAddDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArtistAddDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return (parentFragment.let {
            val selectedItems = ArrayList<Int>() // Where we track the selected items
            val builder = AlertDialog.Builder(requireActivity().baseContext)
            builder.setView(
                View.inflate(
                    requireActivity().baseContext,
                    R.layout.fragment_artist_add_dialog,
                    null
                )
            )
            builder.setTitle("가수 선택")
                .setPositiveButton(
                    "OK",
                    DialogInterface.OnClickListener { dialog, id ->

                    })
                .setNegativeButton(
                    "cancel",
                    DialogInterface.OnClickListener { dialog, id ->

                    })

            builder.create()
        } ?: throw IllegalStateException("Fragment Cannot be null ")) as Dialog
    }
}


/*
*/