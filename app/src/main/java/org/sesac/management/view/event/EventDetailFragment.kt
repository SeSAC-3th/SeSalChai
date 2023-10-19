package org.sesac.management.view.event

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.model.artistList
import org.sesac.management.databinding.FragmentEventDetailBinding



class EventDetailFragment
    : BaseFragment<FragmentEventDetailBinding>(FragmentEventDetailBinding::inflate) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        with(binding) {
            eventToolbar.apply {
                ivBack.setImageResource(R.drawable.baseline_arrow_back_24)
                ivHamburger.setImageResource(R.drawable.baseline_menu_24)
                tvTitle.text="이벤트"
            }
        }
        return binding.root
    }

    override fun onViewCreated() {
        with(binding) {
            eventToolbar.ivBack.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
            rvArtist.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                ).apply {
                    reverseLayout=false
                    stackFromEnd=false
                }
                adapter = EventRecyclerViewAdapter(artistList)
                val spacingInPixels = resources.getDimensionPixelSize(R.dimen.horizontal_margin)
                addItemDecoration(SpaceItemDecoration(spacingInPixels))
            }
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