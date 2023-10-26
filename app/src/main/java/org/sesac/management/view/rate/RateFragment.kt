package org.sesac.management.view.rate

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Artist
import org.sesac.management.databinding.FragmentRateBinding
import org.sesac.management.util.common.Referecne
import org.sesac.management.util.common.Referecne.AVERAGE
import org.sesac.management.util.common.Referecne.DANCE
import org.sesac.management.util.common.Referecne.INCOME
import org.sesac.management.util.common.Referecne.PERFORMACE
import org.sesac.management.util.common.Referecne.POPULARITY
import org.sesac.management.util.common.Referecne.SING
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.util.extension.setOnAvoidDuplicateClickFlow
import org.sesac.management.view.adapter.RateAdapter
import org.sesac.management.view.artist.ArtistViewModel
import org.sesac.management.view.artist.detail.ArtistDetailFragment

class RateFragment : BaseFragment<FragmentRateBinding>(FragmentRateBinding::inflate) {
    val viewModel: ArtistViewModel by activityViewModels()
    override fun onViewCreated() {
        with(binding) {
            layoutRateToolbar.setToolbarMenu("평가", true)

            chipRateAverage.setOnAvoidDuplicateClick {
                viewModel.getAllArtist()
                observeArtistRate(AVERAGE)
            }

            chipRateIncome.setOnAvoidDuplicateClickFlow {
                viewModel.getAllArtist()
                observeArtistRate(INCOME)
            }

            chipRatePopularity.setOnAvoidDuplicateClick {
                viewModel.getAllArtist()
                observeArtistRate(POPULARITY)
            }

            chipRateSing.setOnAvoidDuplicateClick {
                viewModel.getAllArtist()
                observeArtistRate(SING)
            }

            chipRateDance.setOnAvoidDuplicateClick {
                viewModel.getAllArtist()
                observeArtistRate(DANCE)
            }

            chipRatePerformance.setOnAvoidDuplicateClick {
                viewModel.getAllArtist()
                observeArtistRate(PERFORMACE)
            }
        }

        binding.barChartRateCompare.run {
            description.isEnabled = true
            setMaxVisibleValueCount(10)
            setPinchZoom(false)
            setDrawBarShadow(false)
            setDrawGridBackground(false)

            axisLeft.run {
                axisMaximum = 100f
                axisMinimum = 0f
                granularity = 10f
                setDrawLabels(true)
                setDrawGridLines(true)
                setDrawAxisLine(false)
                axisLineColor = ContextCompat.getColor(context, R.color.primary1)
                textColor = ContextCompat.getColor(context, R.color.black)
            }

            xAxis.run {
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                setDrawAxisLine(true)
                setDrawGridLines(false)
                setDrawBarShadow(false)
                textSize = 12f
            }


            axisRight.isEnabled = false // 오른쪽 Y축을 안보이게 해줌.
            setTouchEnabled(false) // 그래프 터치해도 아무 변화없게 막음
            animateY(1000) // 밑에서부터 올라오는 애니매이션 적용
            legend.isEnabled = false //차트 범례 설정
        }


        val manager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL, false
        )

        binding.recyclerRateRanking.layoutManager = manager

    }

    fun observeArtistRate(reference: Referecne) {
        viewModel.getAllArtist.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                var result: List<Artist>
                when (reference) {
                    AVERAGE -> {
                        result = list.sortedByDescending { it.rate?.average ?: 0f }
                    }

                    INCOME -> {
                        result = list.sortedByDescending { it.rate?.income ?: 0 }
                    }

                    POPULARITY -> {
                        result = list.sortedByDescending { it.rate?.popularity ?: 0 }
                    }

                    SING -> {
                        result = list.sortedByDescending { it.rate?.sing ?: 0 }
                    }

                    DANCE -> {
                        result = list.sortedByDescending { it.rate?.dance ?: 0 }
                    }

                    PERFORMACE -> {
                        result = list.sortedByDescending { it.rate?.performance ?: 0 }
                    }

                    else -> {
                        result = listOf<Artist>()
                    }
                }

                with(binding){
                    recyclerRateRanking.adapter = RateAdapter(result) {

                        viewModel.getArtistById(it.artistId)

                        layoutRateMain.changeFragment(this@RateFragment, ArtistDetailFragment())
                    }
                    barChartRateCompare.xAxis.valueFormatter = MyXAxisFormatter(result)

                    val entries = arrayListOf<BarEntry>()
                    for ((idx, value) in result.withIndex()) {
                        if (value.rate != null)
                            entries.add(BarEntry((idx + 1) + 0.2f, value.rate!!.average))
                    }

                    val set = BarDataSet(entries, "DataSet") // 데이터셋 초기화
                    set.color =
                        ContextCompat.getColor(requireContext(), R.color.primary2) // 바 그래프 색 설정

                    set.setColors(
                        Color.parseColor("#FFF78B"),
                        Color.parseColor("#FFD38C"),
                        Color.parseColor("#8DEBFF"),
                        Color.parseColor("#FF8E9C"),
                        Color.parseColor("#C5FF8C")
                    )

                    val data = BarData(listOf(set))

                    data.barWidth = 0.7f //막대 너비 설정

                    barChartRateCompare.run {
                        this.data = data //차트의 데이터를 data로 설정해줌.
                        setFitBars(true)
                        invalidate()
                    }
                }
            }
        }
    }

    inner class MyXAxisFormatter(val artistList: List<Artist>) : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return (artistList.getOrNull(value.toInt() - 1)?.name ?: value.toString())
        }
    }
}