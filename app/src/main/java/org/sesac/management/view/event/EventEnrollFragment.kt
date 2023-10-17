package org.sesac.management.view.event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentEventBinding
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
                /* TODO 가수 등록 화면 으로 전환 */
            }
        }
    }

}