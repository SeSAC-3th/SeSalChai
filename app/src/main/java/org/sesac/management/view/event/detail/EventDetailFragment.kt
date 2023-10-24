package org.sesac.management.view.event.detail

import android.graphics.Rect
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentEventDetailBinding
import org.sesac.management.view.event.EventViewModel
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.view.event.edit.EventEditFragment

class EventDetailFragment
    : BaseFragment<FragmentEventDetailBinding>(FragmentEventDetailBinding::inflate) {
    val viewModel: EventViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated() {
        with(binding) {
            tbEvent.setToolbarMenu("행사 상세", true) {
                eventDetailLayout.changeFragment(this@EventDetailFragment, EventEditFragment())
            }
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