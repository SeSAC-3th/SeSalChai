package org.sesac.management.view.event

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.launch
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Event
import org.sesac.management.data.model.eventList
import org.sesac.management.databinding.FragmentEventBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.view.adapter.recyclerview.EventRecyclerAdapter
import org.sesac.management.view.event.detail.EventDetailFragment
import org.sesac.management.view.event.enroll.EventEnrollFragment
import java.util.Date

class EventFragment : BaseFragment<FragmentEventBinding>(FragmentEventBinding::inflate) {
    val TAG: String = "로그"
    private val viewModel: EventViewModel by viewModels()

    override fun onViewCreated() {
        lifecycleScope.launch {
            viewModel.insertEvent(
                Event(
                    name = "새싹카운트다운", place = "상암 Mnet", date = Date(), description = "새싹 3기",
                    imgUri = null
                )
            )
            //INSERT INTO artist (name, member_info, debut_day, type, rate_id, img_uri)
            //VALUES ('르세라핌', '신진혁, 함우빈, 최종혁', '2023-10-23', 'SINGER', 1, '이미지 URI');
        }
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