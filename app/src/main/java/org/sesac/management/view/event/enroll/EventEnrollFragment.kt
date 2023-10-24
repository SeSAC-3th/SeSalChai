package org.sesac.management.view.event.enroll

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentEventEnrollBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.view.event.dialog.ArtistAddDialogFragment

class EventEnrollFragment :
    BaseFragment<FragmentEventEnrollBinding>(FragmentEventEnrollBinding::inflate) {
    /* 선택한 이미지 절대경로 가져오기 */
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            binding.ivSchedule.setImageURI(uri)
        }
        /* 선택한 이미지 Uri 처리 */
    }
    override fun onViewCreated() {
        with(binding) {
            tbScheduleEnroll.setToolbarMenu("행사 등록", true)
            ivSchedule.setOnClickListener {
                getContent.launch("image/*")
            }
            /* Enroll Button Click */
            with(ivAdd) {
                setOnAvoidDuplicateClick {
                    eventEnrollLayout.changeFragment(
                        this@EventEnrollFragment,
                        ArtistAddDialogFragment()
                    )
                }
            }
        }
    }
}