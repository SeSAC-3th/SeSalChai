package org.sesac.management.view.event.edit

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.model.DialogItem
import org.sesac.management.databinding.FragmentEventEditBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.view.event.dialog.ArtistAddDialogFragment
import org.sesac.management.view.event.dialog.DialogAdapter

class EventEditFragment :
    BaseFragment<FragmentEventEditBinding>(FragmentEventEditBinding::inflate) {

    override fun onViewCreated() {
        with(binding) {
            tbScheduleEnroll.setToolbarMenu("행사 수정", true) {
                //  수정 버튼 클릭 이벤트
            }
            with(ivAdd) {
                setOnAvoidDuplicateClick {
                    eventEditLayout.changeFragment(
                        this@EventEditFragment,
                        ArtistAddDialogFragment()
                    )
                }
            }
        }
    }
}