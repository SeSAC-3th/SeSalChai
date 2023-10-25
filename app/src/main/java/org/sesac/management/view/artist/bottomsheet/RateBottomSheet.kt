package org.sesac.management.view.artist.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.sesac.management.databinding.FragmentRateBottomSheetBinding
import org.sesac.management.view.artist.ArtistViewModel

class RateBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentRateBottomSheetBinding
    private val viewModel: ArtistViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
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
