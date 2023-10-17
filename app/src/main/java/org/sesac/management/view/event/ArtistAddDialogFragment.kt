package org.sesac.management.view.event

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ArtistAddDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return (parentFragment.let {
            /*  TODO Dialog 생성 */
//            val selectedItems = ArrayList<Int>() // Where we track the selected items
//            val builder = AlertDialog.Builder(requireActivity().baseContext)
//            builder.setTitle("가수 선택")
//                .setMultiChoiceItems(
//                    , null,
//                    DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
//                        if (isChecked) {
//                            // If the user checked the item, add it to the selected items
//                            selectedItems.add(which)
//                        } else if (selectedItems.contains(which)) {
//                            // Else, if the item is already in the array, remove it
//                            selectedItems.remove(which)
//                        }
//                    })
//                // Set the action buttons
//                .setPositiveButton(
//                    "OK",
//                    DialogInterface.OnClickListener { dialog, id ->
//                      /* TODO 이벤트 처리 */
//                    })
//                .setNegativeButton(
//                    R.string.cancel,
//                    DialogInterface.OnClickListener { dialog, id ->
//                        ...
//                    })
//
//            builder.create()
        } ?: throw IllegalStateException("Fragment Cannot be null ")) as Dialog
    }
}