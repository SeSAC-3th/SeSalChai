package org.sesac.management.view.event.edit

import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentEventEditBinding
import org.sesac.management.view.event.dialog.ArtistAddDialogFragment

class EventEditFragment :
    BaseFragment<FragmentEventEditBinding>(FragmentEventEditBinding::inflate) {

    override fun onViewCreated() {
        with(binding) {
            tbScheduleEnroll.setToolbarMenu("행사 수정", true) {
                //  수정 버튼 클릭 이벤트
            }
            with(ivAdd) {
                setOnAvoidDuplicateClick {
                    val addDialog = ArtistAddDialogFragment()
                    activity?.let { addDialog.show(childFragmentManager, "artistDialogFragment") }
                }
            }
        }
    }
}