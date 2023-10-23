package org.sesac.management.view.notice.enroll

import androidx.fragment.app.viewModels
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.room.Notice
import org.sesac.management.databinding.FragmentNoticeEnrollBinding
import org.sesac.management.view.notice.NoticeViewModel
import java.util.Date

class NoticeEnrollFragment() :
    BaseFragment<FragmentNoticeEnrollBinding>(FragmentNoticeEnrollBinding::inflate) {

    private val noticeViewModel : NoticeViewModel by viewModels()
    override fun onViewCreated() {

        with(binding) {
            toolbarNoticeEnroll.setToolbarMenu("공지사항 등록", true)

            //취소 버튼 클릭시 backKey와 동일하게 작동
            btnCancel.setOnAvoidDuplicateClick {
                backPress()
            }


            btnSave.setOnAvoidDuplicateClick {
                val notice= Notice(
                    binding.etTitle.text.toString(),
                    binding.etContent.text.toString(),
                    Date(),
                )
                noticeViewModel.insertNoticeInfo(notice)
            }
        }
    }

}