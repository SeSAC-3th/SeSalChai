package org.sesac.management.view.event.enroll

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentEventEnrollBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.view.event.EventViewModel
import org.sesac.management.view.event.dialog.ArtistAddDialogFragment


class EventEnrollFragment :
    BaseFragment<FragmentEventEnrollBinding>(FragmentEventEnrollBinding::inflate) {
    val viewModel: EventViewModel by viewModels({ requireParentFragment() })
    /* 선택한 이미지 절대경로 가져오기 */
    val contentResolver: ContentResolver? = context?.contentResolver
    private var bitmap: Bitmap? = null
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.ivSchedule.setImageURI(uri)
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap = MediaStore.Images.Media
                        .getBitmap(contentResolver, uri)
                } else {
                    val decode = this.contentResolver?.let { it1 ->
                        ImageDecoder.createSource(
                            it1,
                            uri
                        )
                    }
                    bitmap = decode?.let { it1 -> ImageDecoder.decodeBitmap(it1) }
                }
            }
        }
    /* 선택한 이미지 Uri 처리 */

    override fun onViewCreated() {
        with(binding) {
            tbScheduleEnroll.setToolbarMenu("행사 등록", true) {
                // 작성
            }
            ivSchedule.setOnClickListener {
                getContent.launch("image/*")
            }
            /* Enroll Button Click */
            with(ivAdd) {
                setOnAvoidDuplicateClick {
                    val addDialog= ArtistAddDialogFragment()
                    activity?.let { addDialog.show(childFragmentManager, "artistDialogFragment") }
                }
            }
        }
    }
}