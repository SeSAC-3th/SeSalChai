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
    lateinit var callback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNoticeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backpress()
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

    fun backpress() {
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (parentFragmentManager.backStackEntryCount > 0) {
                    parentFragmentManager.popBackStackImmediate(null, 0)
                } else {
                    requireActivity().finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}