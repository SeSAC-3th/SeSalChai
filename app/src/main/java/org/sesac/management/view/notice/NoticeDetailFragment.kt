package org.sesac.management.view.notice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentHomeBinding
import org.sesac.management.databinding.FragmentNoticeDetailBinding

class NoticeDetailFragment() :
    BaseFragment<FragmentNoticeDetailBinding>(FragmentNoticeDetailBinding::inflate) {
    // 뒤로가기 버튼을 눌렀을 때를 위한 callback 변수

    override fun onViewCreated() {
        with(binding) {
            toolbarNoticeDetail.tvTitle.text = "공지사항"
        }
    }


}