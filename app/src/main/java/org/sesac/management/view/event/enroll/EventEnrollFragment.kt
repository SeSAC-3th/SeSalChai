package org.sesac.management.view.event.enroll

import androidx.fragment.app.viewModels
import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentEventEnrollBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.view.event.EventViewModel
import org.sesac.management.view.event.dialog.ArtistAddDialogFragment

class EventEnrollFragment :
    BaseFragment<FragmentEventEnrollBinding>(FragmentEventEnrollBinding::inflate) {
    val viewModel: EventViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated() {
        with(binding) {
            tbScheduleEnroll.setToolbarMenu("행사 등록", true)
            /* Enroll Button Click */
            with(ivAdd) {
                setOnAvoidDuplicateClick {
                    eventEnrollLayout.changeFragment(
                        this@EventEnrollFragment,
                        ArtistAddDialogFragment()
                    )
                }
            }
        }
    }
}