package org.sesac.management.view.event

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.model.eventList
import org.sesac.management.databinding.FragmentEventBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.view.adapter.recyclerview.EventRecyclerAdapter
import org.sesac.management.view.event.detail.EventDetailFragment
import org.sesac.management.view.event.enroll.EventEnrollFragment

class EventFragment : BaseFragment<FragmentEventBinding>(FragmentEventBinding::inflate) {
    val TAG: String = "로그"
    private val viewModel: EventViewModel by viewModels()

    override fun onViewCreated() {
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