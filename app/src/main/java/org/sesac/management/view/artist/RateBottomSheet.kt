package org.sesac.management.view.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.sesac.management.databinding.FragmentRateBottomSheetBinding

class RateBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentRateBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRateBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
        
            with(btnSave) {
            }
        }
    }


}
