package org.sesac.management.view.event

import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentEventEnrollBinding

class EventEnrollFragment :
    BaseFragment<FragmentEventEnrollBinding>(FragmentEventEnrollBinding::inflate) {

    override fun onViewCreated() {
        with(binding) {
            scheduleEnrollToolbar.setToolbarMenu("행사 등록", true)
            
            ivAdd.setOnClickListener {
                childFragmentManager.beginTransaction()
                    .add(R.id.event_enroll_layout, ArtistAddDialogFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        }
    }

}