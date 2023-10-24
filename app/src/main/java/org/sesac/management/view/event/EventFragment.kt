package org.sesac.management.view.event

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.launch
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.model.eventList
import org.sesac.management.data.local.Event
import org.sesac.management.databinding.FragmentEventBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.view.adapter.recyclerview.ArtistRecyclerAdapter
import org.sesac.management.view.event.detail.EventDetailFragment
import org.sesac.management.view.event.enroll.EventEnrollFragment
import java.util.Date


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
                adapter = ArtistRecyclerAdapter(
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