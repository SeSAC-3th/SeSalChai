package org.sesac.management.view.event.enroll

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.util.convertUriToBitmap
import org.sesac.management.databinding.FragmentEventEnrollBinding
import org.sesac.management.util.common.ARTIST
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.view.event.EventViewModel
import org.sesac.management.view.event.dialog.ArtistAddDialogFragment


class EventEnrollFragment :
    BaseFragment<FragmentEventEnrollBinding>(FragmentEventEnrollBinding::inflate) {
    val viewModel: EventViewModel by viewModels({ requireParentFragment() })
    /* 선택한 이미지 절대경로 가져오기 */
    //* bitmap을 insert할때 넘겨주면 됩니다
    private var bitmap: Bitmap? = null
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                Log.d(ARTIST, "uri: $uri")
                binding.ivSchedule.setImageURI(uri)
                context?.let { it1 ->
                    var tmpBitmap = convertUriToBitmap(uri, it1)
                    tmpBitmap?.let { it2 -> bitmap = it2 }
                }
            }
        }
    /* 선택한 이미지 Uri 처리 */

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