package org.sesac.management.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentEventBinding
import org.sesac.management.databinding.FragmentHomeBinding
import org.sesac.management.util.extension.setCoroutineFlowClickAction
import org.sesac.management.view.artist.ArtistDetailFragment
import org.sesac.management.view.artist.ArtistEnrollFragment
import org.sesac.management.view.notice.NoticeFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvHomeCompName.text = resources.getString(R.string.title_home_comp_name)
        binding.tvHomeCompInfo.text = resources.getString(R.string.title_home_comp_info)
    }
}