package org.sesac.management.view.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.model.artistList
import org.sesac.management.data.model.eventList
import org.sesac.management.databinding.FragmentEventBinding
import org.sesac.management.view.artist.ArtistDetailFragment
import org.sesac.management.view.artist.ArtistRecyclerAdapter


class EventFragment : BaseFragment<FragmentEventBinding>(FragmentEventBinding::inflate) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEventBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated() {
        with(binding.rvEvent){
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