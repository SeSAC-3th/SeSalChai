package org.sesac.management.view.event

import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentEventEnrollBinding
import org.sesac.management.util.extension.changeFragment

class EventEnrollFragment :
    BaseFragment<FragmentEventEnrollBinding>(FragmentEventEnrollBinding::inflate) {

    override fun onViewCreated() {
        with(binding) {
            tbScheduleEnroll.setToolbarMenu("행사 등록", true)
            /* Enroll Button Click */
            with(ivAdd) {
                setOnAvoidDuplicateClick {
                    eventEnrollLayout.changeFragment(this@EventEnrollFragment, ArtistAddDialogFragment())
                }
            }
        }
    }

}