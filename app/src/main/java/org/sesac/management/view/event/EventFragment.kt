package org.sesac.management.view.event

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.launch
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.model.eventList
import org.sesac.management.data.room.Event
import org.sesac.management.databinding.FragmentEventBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.view.artist.ArtistRecyclerAdapter
import java.util.Date


class EventFragment : BaseFragment<FragmentEventBinding>(FragmentEventBinding::inflate) {
    val TAG: String = "로그"
    private val viewModel: EventViewModel by viewModels()

    override fun onViewCreated() {
        lifecycleScope.launch {
            viewModel.insertEvent(
                Event(
                    name = "새싹카운트다운", place = "상암 Mnet", date = Date(), description = "새싹 3기",
                    imgUri = "이미지 URI"
                )
            )
        }

        with(binding.rvEvent) {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = ArtistRecyclerAdapter(
                eventList,
                onClick = {
                    binding.eventLayout.changeFragment(this@EventFragment, EventDetailFragment())
                }
            )
        }
    }
}