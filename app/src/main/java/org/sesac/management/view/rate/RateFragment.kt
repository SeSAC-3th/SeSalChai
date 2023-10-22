package org.sesac.management.view.rate

import android.graphics.Color
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentRateBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.util.extension.setOnAvoidDuplicateClickFlow
import org.sesac.management.view.artist.detail.ArtistDetailFragment

class RateFragment : BaseFragment<FragmentRateBinding>(FragmentRateBinding::inflate) {

    /* events 임시 데이터 */
    private val events = mutableListOf<String>(
        "새싹1기",
        "새싹2기",
        "새싹3기",
        "새싹4기"
    )

    val entries = arrayListOf<BarEntry>(
        BarEntry(1.2f, 20.0f),
        BarEntry(2.2f, 70.0f),
        BarEntry(3.2f, 30.0f),
        BarEntry(4.2f, 90.0f),
        BarEntry(5.2f, 70.0f),
        BarEntry(6.2f, 30.0f),
        BarEntry(7.2f, 90.0f)
    )

    override fun onViewCreated() {
        with(binding) {
            layoutRateToolbar.setToolbarMenu("평가", true)

            chipRateAverage.setOnAvoidDuplicateClick {
                binding.layoutRateMain.changeFragment(this@RateFragment, ArtistDetailFragment())
            }
        }

        binding.chipRateIncome.setOnAvoidDuplicateClickFlow {
        }

        binding.chipRatePopularity.setOnAvoidDuplicateClick {

        }

        binding.chipRateSing.setOnAvoidDuplicateClick {

        }

        binding.chipRateDance.setOnAvoidDuplicateClick {

        }

        binding.chipRatePerformance.setOnAvoidDuplicateClick {

        }
        binding.test.setOnFinishInput {
            binding.test2.text = it
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
                valueFormatter = MyXAxisFormatter() // X축 라벨값(밑에 표시되는 글자) 바꿔주기 위해 설정
            }

            axisRight.isEnabled = false // 오른쪽 Y축을 안보이게 해줌.
            setTouchEnabled(false) // 그래프 터치해도 아무 변화없게 막음
            animateY(1000) // 밑에서부터 올라오는 애니매이션 적용
            legend.isEnabled = false //차트 범례 설정
        }


        var set = BarDataSet(entries, "DataSet") // 데이터셋 초기화
        set.color = ContextCompat.getColor(requireContext(), R.color.primary2) // 바 그래프 색 설정
        set.colors

        set.setColors(
            Color.parseColor("#FFF78B"),
            Color.parseColor("#FFD38C"),
            Color.parseColor("#8DEBFF"),
            Color.parseColor("#FF8E9C"),
            Color.parseColor("#C5FF8C"),
//            R.color.rateBar1,
//            R.color.rateBar2,
//            R.color.rateBar3,
//            R.color.rateBar4,
//            R.color.rateBar5
        )


        val dataSet: ArrayList<IBarDataSet> = ArrayList()
        dataSet.add(set)
        val data = BarData(dataSet)
        data.barWidth = 0.5f //막대 너비 설정
        binding.barChartRateCompare.run {
            this.data = data //차트의 데이터를 data로 설정해줌.
            setFitBars(true)
            invalidate()
        }

        val manager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL, false
        )

        with(binding.recyclerRateRanking) {
            layoutManager = manager
            adapter = RateAdapter(events) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                binding.layoutRateMain.changeFragment(this@RateFragment, ArtistDetailFragment())
            }
        }
    }

    inner class MyXAxisFormatter : ValueFormatter() {
        private val days = arrayOf("1차", "2차", "3차", "4차", "5차", "6차", "7차")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return days.getOrNull(value.toInt() - 1) ?: value.toString()
        }
    }
}