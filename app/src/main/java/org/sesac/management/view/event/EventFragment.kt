package org.sesac.management.view.event

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.launch
import org.sesac.management.base.BaseFragment
<<<<<<< HEAD
import org.sesac.management.data.model.eventList
=======
import org.sesac.management.data.local.Event
>>>>>>> 79b8fddb51bbd1750d5d29c1da760f08097e3bf3
import org.sesac.management.databinding.FragmentEventBinding
import org.sesac.management.util.common.ApplicationClass.Companion.getApplicationContext
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.view.adapter.recyclerview.EventRecyclerAdapter
import org.sesac.management.view.event.detail.EventDetailFragment
import org.sesac.management.view.event.enroll.EventEnrollFragment

class EventFragment : BaseFragment<FragmentEventBinding>(FragmentEventBinding::inflate) {
    val TAG: String = "로그"
    val viewModel: EventViewModel by viewModels() {
        EventViewModel.EventViewModelFactory(getApplicationContext().eventRepository)
    }

    override fun onViewCreated() {
        // flow-flow
        /**
         * R : 전체 이벤트 데이터 정보 읽기 메서드
         * getAllEvent의 메서드를 불러와 List<Event>를 RecyclerView에 추가한다.
         * @author 혜원
         */
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSearch()
                viewModel.event.collect { event ->
                    makeList(event)
                }
            }
        }

        // livedata-flow
        viewModel.getEventByID(1).observe(viewLifecycleOwner) {
            if (it != null) {

            }
        }

        // livedata-livedata
        viewModel.getEventByName("").observe(viewLifecycleOwner) {
            if (it != null) {
            }
        }

        with(binding) {
            /* Enroll Button */
            with(btnEventEnroll) {
                setOnAvoidDuplicateClick {
                    eventLayout.changeFragment(this@EventFragment, EventEnrollFragment())
                }
            }
        }
    }
    private fun makeList(eventList: List<Event>) {
        /* RecyclerView */
        with(binding.rvEvent) {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = EventRecyclerAdapter(
                eventList,
                onDelete = {
                    viewModel.deleteEvent(it.eventId)
                },
                onClick = {
                    binding.eventLayout.changeFragment(
                        this@EventFragment,
                        EventDetailFragment()
                    )
                }
            )
        }
    }
}