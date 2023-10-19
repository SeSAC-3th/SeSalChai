package org.sesac.management.view.event

import androidx.recyclerview.widget.GridLayoutManager
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.model.eventList
import org.sesac.management.databinding.FragmentEventBinding
import org.sesac.management.view.artist.ArtistRecyclerAdapter


class EventFragment : BaseFragment<FragmentEventBinding>(FragmentEventBinding::inflate) {

    override fun onViewCreated() {

        with(binding.rvEvent) {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = ArtistRecyclerAdapter(
                eventList, childFragmentManager,
                EventDetailFragment(),
                R.id.event_layout
            )
        }

        binding.btnEventEnroll.setOnClickListener {
            childFragmentManager.beginTransaction()
                .add(R.id.event_layout,EventEnrollFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

    }
}