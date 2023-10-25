package org.sesac.management.view.artist.bottomsheet

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.sesac.management.R
import org.sesac.management.data.local.Rate
import org.sesac.management.databinding.FragmentRateBottomSheetBinding
import org.sesac.management.util.common.showToastMessage
import org.sesac.management.util.extension.setOnAvoidDuplicateClickFlow
import org.sesac.management.view.artist.ArtistViewModel

class RateBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentRateBottomSheetBinding
    private val viewModel: ArtistViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRateBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val artistId = arguments?.getInt("artistId") ?: -1
        val rateId = arguments?.getInt("rateId") ?: -1
        if (rateId != -1) {
            showToastMessage(resources.getString(R.string.artist_enroll_rate))
        } else {
            with(binding) {
                btnSave.setOnAvoidDuplicateClickFlow {
                    val earnings = sliderRevenue.value.toInt()
                    val popularity = sliderPopularity.value.toInt()
                    val dance = sliderDance.value.toInt()
                    val performance = sliderPerform.value.toInt()
                    val sing = sliderSing.value.toInt()

                    viewModel.insertRateWithArtist(
                        Rate(
                            earnings,
                            popularity,
                            sing,
                            dance,
                            performance,
                            0
                        ), artistId
                    )
                    showToastMessage(resources.getString(R.string.artist_enroll_success))
                    dismiss()
                }
            }
        }
    }
}
