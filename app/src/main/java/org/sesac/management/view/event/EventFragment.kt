package org.sesac.management.view.event

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.launch
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.model.eventList
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect {
                    // Update UI elements
                }
            }
        }

        // livedata-flow
        viewModel.eventByID(1).observe(viewLifecycleOwner) {
            if (it != null) {

            }
        }

        // livedata-livedata
        viewModel.eventByName("").observe(viewLifecycleOwner) {
            if (it != null) {

            }
        }

//        lifecycleScope.launch {
//            viewModel.insertEvent(
//                Event(
//                    name = "새싹카운트다운", place = "상암 Mnet", date = Date(), description = "새싹 3기",
//                    imgUri = "이미지 URI"
//                )
//            )
//            //INSERT INTO artist (name, member_info, debut_day, type, rate_id, img_uri)
//            //VALUES ('르세라핌', '신진혁, 함우빈, 최종혁', '2023-10-23', 'SINGER', 1, '이미지 URI');
//        }

        with(binding) {
            /* Enroll Button */
            with(btnEventEnroll) {
                setOnAvoidDuplicateClick {
                    eventLayout.changeFragment(this@EventFragment, EventEnrollFragment())
                }
            }

            /* RecyclerView */
            with(rvEvent) {
                layoutManager = GridLayoutManager(activity, 2)
                adapter = EventRecyclerAdapter(
                    eventList,
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
}