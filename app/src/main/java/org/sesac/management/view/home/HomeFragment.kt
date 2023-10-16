package org.sesac.management.view.home

import android.os.Bundle
import android.view.View
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvHomeCompName.text = resources.getString(R.string.title_home_comp_name)
        binding.tvHomeCompInfo.text = resources.getString(R.string.title_home_comp_info)
    }
}