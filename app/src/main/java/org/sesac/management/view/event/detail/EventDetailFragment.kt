package org.sesac.management.view.event.detail

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.model.artistList
import org.sesac.management.databinding.FragmentEventDetailBinding



class EventDetailFragment
    : BaseFragment<FragmentEventDetailBinding>(FragmentEventDetailBinding::inflate) {

    override fun onViewCreated() {
        with(binding) {
            tbEvent.setToolbarMenu("행사 상세", true)
        }
    }
    inner class SpaceItemDecoration(private val space : Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.left=space
            outRect.right=space
        }
    }


}