package org.sesac.management.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.sesac.management.R
import org.sesac.management.databinding.FragmentEventBinding
import org.sesac.management.databinding.FragmentHomeBinding
import org.sesac.management.view.artist.ArtistDetailFragment
import org.sesac.management.view.artist.ArtistEnrollFragment
import org.sesac.management.view.notice.NoticeFragment

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.notice.setOnClickListener {
            childFragmentManager
                .beginTransaction()
                .add(binding.homeLayout.id, NoticeFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }
}