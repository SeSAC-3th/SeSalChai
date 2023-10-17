package org.sesac.management.view.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.model.ArtistThumbnail
import org.sesac.management.data.model.artistList
import org.sesac.management.databinding.FragmentEventDetailBinding
import org.sesac.management.view.artist.ArtistEventViewPagerAdapter


class EventDetailFragment
    : BaseFragment<FragmentEventDetailBinding>(FragmentEventDetailBinding::inflate) {
//        private lateinit var eventDetailAdapter : EventRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEventDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated() {
        with(binding) {
            eventToolbar.tvTitle.text = getString(R.string.event_detail_toolbar_text)
            rvArtist.apply {
                layoutManager = LinearLayoutManager(
                    activity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapter=EventRecyclerViewAdapter(artistList)
            }
        }
    }

}