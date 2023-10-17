package org.sesac.management.view.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.model.artistList
import org.sesac.management.databinding.FragmentArtistBinding

class ArtistFragment : BaseFragment<FragmentArtistBinding>(FragmentArtistBinding::inflate) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentArtistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated() {
        binding.chipGroupSort.setOnClickListener {
            childFragmentManager
                .beginTransaction()
                .add(binding.artistLayout.id, ArtistDetailFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        binding.chipDebutSort.setOnClickListener {
            childFragmentManager
                .beginTransaction()
                .add(binding.artistLayout.id, ArtistEnrollFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        with(binding.rvArtist){
            layoutManager = GridLayoutManager(activity, 2)
            adapter = ArtistRecyclerAdapter(artistList)
        }
    }

}