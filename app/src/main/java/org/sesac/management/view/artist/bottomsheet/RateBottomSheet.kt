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
import org.sesac.management.data.model.Artist
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

        val bundle = arguments?.getSerializable("artistBundle", Artist::class.java)
        with(binding) {
            btnSave.setOnAvoidDuplicateClickFlow {
                val earnings = sliderRevenue.value.toInt()
                val popularity = sliderPopularity.value.toInt()
                val dance = sliderDance.value.toInt()
                val performance = sliderPerform.value.toInt()
                val sing = sliderSing.value.toInt()

                if (bundle != null) {
                    if (bundle.rateId == null) {
                        bundle.rateId?.let {
                            viewModel.insertRateWithArtist(
                                Rate(
                                    earnings,
                                    popularity,
                                    sing,
                                    dance,
                                    performance,
                                    0
                                ), it
                            )
                        }
                        showToastMessage(resources.getString(R.string.artist_enroll_success))
                        dismiss()
                    } else {
                        showToastMessage(resources.getString(R.string.artist_enroll_rate))
                        dismiss()
                    }
                } else {
                    showToastMessage(resources.getString(R.string.artist_enroll_fail))
                    dismiss()
                }
            }
        }
    }
}
