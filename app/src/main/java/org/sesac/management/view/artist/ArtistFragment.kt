package org.sesac.management.view.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.sesac.management.databinding.FragmentArtistBinding

class ArtistFragment : Fragment() {
    private lateinit var binding: FragmentArtistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentArtistBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detail.setOnClickListener {
            childFragmentManager
                .beginTransaction()
                .add(binding.artistLayout.id, ArtistDetailFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        binding.enroll.setOnClickListener {
            childFragmentManager
                .beginTransaction()
                .add(binding.artistLayout.id, ArtistEnrollFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }

}