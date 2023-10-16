package org.sesac.management.view.notice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import org.sesac.management.R
import org.sesac.management.databinding.FragmentEventBinding
import org.sesac.management.databinding.FragmentNoticeBinding
import org.sesac.management.view.artist.ArtistDetailFragment
import org.sesac.management.view.artist.ArtistEnrollFragment

class NoticeFragment : Fragment() {
    private lateinit var binding: FragmentNoticeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNoticeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detail.setOnClickListener {
            childFragmentManager
                .beginTransaction()
                .add(binding.noticeLayout.id, NoticeDetailFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        binding.enroll.setOnClickListener {
            childFragmentManager
                .beginTransaction()
                .add(binding.noticeLayout.id, NoticeEnrollFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }

}