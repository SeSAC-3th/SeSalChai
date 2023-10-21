package org.sesac.management.view.event

import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding4.view.changeEvents
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.model.eventList
import org.sesac.management.databinding.FragmentEventBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.view.artist.ArtistRecyclerAdapter


class EventFragment : BaseFragment<FragmentEventBinding>(FragmentEventBinding::inflate) {

    override fun onViewCreated() {

        with(binding.rvEvent) {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = ArtistRecyclerAdapter(
                eventList,
                onClick = {
                    binding.eventLayout.changeFragment(this@EventFragment, EventDetailFragment())
                }
            )
        }
    }
}