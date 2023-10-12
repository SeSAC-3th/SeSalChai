package org.sesac.management.view.event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.sesac.management.R
import org.sesac.management.databinding.FragmentArtistBinding
import org.sesac.management.databinding.FragmentEventBinding
import org.sesac.management.view.artist.ArtistDetailFragment
import org.sesac.management.view.artist.ArtistEnrollFragment


class EventFragment : Fragment() {
    private lateinit var binding: FragmentEventBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEventBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detail.setOnClickListener {
            childFragmentManager
                .beginTransaction()
                .add(binding.eventLayout.id, EventDetailFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        binding.enroll.setOnClickListener {
            childFragmentManager
                .beginTransaction()
                .add(binding.eventLayout.id, EventEnrollFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }
}