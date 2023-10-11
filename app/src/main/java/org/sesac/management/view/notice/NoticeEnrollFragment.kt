package org.sesac.management.view.notice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import org.sesac.management.R

class NoticeEnrollFragment : Fragment() {

    // 뒤로가기 버튼을 눌렀을 때를 위한 callback 변수
    lateinit var callback: OnBackPressedCallback
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notice_enroll, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backpress()
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