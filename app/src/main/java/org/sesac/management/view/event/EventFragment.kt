package org.sesac.management.view.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.model.artistList
import org.sesac.management.data.model.eventList
import org.sesac.management.databinding.FragmentEventBinding
import org.sesac.management.view.artist.ArtistRecyclerAdapter


class EventFragment : BaseFragment<FragmentEventBinding>(FragmentEventBinding::inflate) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEventBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.rvEvent){
            layoutManager = GridLayoutManager(activity, 2)
            adapter = ArtistRecyclerAdapter(eventList)
        }
    }
}