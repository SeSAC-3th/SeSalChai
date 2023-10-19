package org.sesac.management.view.event

import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentEventDetailBinding


class EventDetailFragment
    : BaseFragment<FragmentEventDetailBinding>(FragmentEventDetailBinding::inflate) {

    override fun onViewCreated() {
        with(binding) {
            eventToolbar.setToolbarMenu("행사 상세", true)
        }
    }

}