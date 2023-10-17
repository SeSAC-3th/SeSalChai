package org.sesac.management.view.notice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentNoticeDetailBinding
import org.sesac.management.databinding.FragmentNoticeEnrollBinding

class NoticeEnrollFragment() : BaseFragment<FragmentNoticeEnrollBinding>(FragmentNoticeEnrollBinding::inflate) {


    override fun onViewCreated() {
        with(binding) {
            //toolbar 제목 설정
            toolbarNoticeEnroll.tvTitle.text = "공지사항 등록"

            //취소 버튼 클릭시 backKey와 동일하게 작동
            btnCancel.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

        }
    }


}