package org.sesac.management.view.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentArtistAddDialogBinding
import org.sesac.management.databinding.FragmentEventEnrollBinding

class EventEnrollFragment : BaseFragment<FragmentEventEnrollBinding>
    (FragmentEventEnrollBinding::inflate) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEventEnrollBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated() {
        with(binding) {
            ivAdd.setOnClickListener {
                childFragmentManager.beginTransaction()
                    .add(R.id.event_enroll_layout, ArtistAddDialogFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        }
    }

}