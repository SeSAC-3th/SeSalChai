package org.sesac.management.view.artist.bottomsheet

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.sesac.management.R
import org.sesac.management.data.local.Rate
import org.sesac.management.databinding.FragmentRateBottomSheetBinding
import org.sesac.management.util.common.showToastMessage
import org.sesac.management.util.extension.setOnAvoidDuplicateClickFlow
import org.sesac.management.view.artist.ArtistViewModel

/**
 * Rate bottom sheet
 *
 * @constructor Create empty Rate bottom sheet
 * @author 우빈
 */
class RateBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentRateBottomSheetBinding
    private val viewModel: ArtistViewModel by activityViewModels()

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

        val artistId = viewModel.getArtistDetail.value?.artistId ?: -1
        val rateId = viewModel.getArtistDetail.value?.rate ?: -1
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
                    val average = (earnings + popularity + dance + performance + sing) / 5f

                    viewModel.insertRateWithArtist(
                        Rate(
                            average,
                            earnings,
                            popularity,
                            sing,
                            dance,
                            performance,
                            0
                        ), artistId
                    )

                    showToastMessage(resources.getString(R.string.artist_enroll_success))

                    this@RateBottomSheet.dismiss()
                }
            }
        }
    }
}
