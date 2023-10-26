package org.sesac.management.view.event.detail

import android.graphics.Rect
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Event
import org.sesac.management.databinding.FragmentEventDetailBinding
import org.sesac.management.view.event.EventViewModel
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.view.event.edit.EventEditFragment

class EventDetailFragment
    : BaseFragment<FragmentEventDetailBinding>(FragmentEventDetailBinding::inflate) {
    val viewModel: EventViewModel by viewModels({ requireParentFragment() })
    private var eventId = 0

    private fun observeData() {
        viewModel.getEventDetail.observe(viewLifecycleOwner) { event ->
            eventId = event.eventId
            getEventDetail(event)
        }
    }

    override fun onViewCreated() {
        observeData()
        with(binding) {
            tbEvent.setToolbarMenu("행사 상세", true) {
                eventDetailLayout.changeFragment(this@EventDetailFragment, EventEditFragment())
            }
        }
    }
    private fun getEventDetail(event : Event) {
        with(binding) {
            //행사 정보
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvEventTitle.text = event.name
                tvEventTime.text = "일시 : ${SimpleDateFormat("yyyy-MM-dd").format(event.date)}"
                tvEventPlace.text = "장소 : ${event.place}"
            }
            //이미지
            event.imgUri?.let {
                ivEvent.setImageBitmap(it)
            } ?: ivEvent.setImageResource(R.drawable.girls_generation_hyoyeon)
        }

    }

    inner class SpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.left = space
            outRect.right = space
        }
    }


}