package org.sesac.management.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.sesac.management.databinding.FragmentHomeBinding
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