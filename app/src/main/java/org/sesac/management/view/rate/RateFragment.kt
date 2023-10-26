package org.sesac.management.view.rate

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
import org.sesac.management.util.common.defaultDispatcher
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.util.extension.setOnAvoidDuplicateClickFlow
import org.sesac.management.view.adapter.RateAdapter
import org.sesac.management.view.artist.ArtistViewModel
import org.sesac.management.view.artist.detail.ArtistDetailFragment

class RateFragment : BaseFragment<FragmentRateBinding>(FragmentRateBinding::inflate) {
    val viewModel: ArtistViewModel by activityViewModels()
    override fun onViewCreated() {
        binding.layoutRateToolbar.setToolbarMenu("평가", true) // toolbar 세팅
        viewModel.getAllArtist() // 아티스트 정보 호출
        initBarGraph() // 그래프 구성 세팅
        observeArtistRate() // 초기 아티스트 정보를 Rate.Average를 기준으로 정렬한다.
        observeSortedArtistRate() // 정렬된 아티스트 정보를 관찰한다.
    }

    /**
     * Observe artist rate : 모든 아티스트 목록 정보를 Observe하고, chip에 선택된 값을 기준으로 정렬해준다.
     *
     * 아티스트 정보가 갱신되면 클릭된 chip reference 정보를 바탕으로 정렬해서 보여준다.
     *
     * 클릭된 chip reference 정보는 초기 값이 AVERAGE(종합)이다.
     *
     * 아티스트 정보를 가지고 chip 클릭 이벤트를 초기화 시켜준다.
     *
     * @param reference : 정렬 기준
     * @author 진혁
     */
    fun observeArtistRate() {
        viewModel.getAllArtist.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                viewModel.getAllSortedArtist(viewModel.rateReferecne, list)
                setClickEventOnChips(list)
            }
        }
    }

    /**
     * Set click event on chips : 클릭된 chip을 기준으로 아티스트 목록을 정렬한다.
     *
     * @param list : 모든 아티스트 정보
     * @author 진혁
     */
    private fun setClickEventOnChips(list: List<Artist>) {
        with(binding) {
            chipRateAverage.setOnAvoidDuplicateClick {
                viewModel.getAllSortedArtist(AVERAGE, list)
            }

            chipRateIncome.setOnAvoidDuplicateClickFlow {
                viewModel.getAllSortedArtist(INCOME, list)
            }

            chipRatePopularity.setOnAvoidDuplicateClick {
                viewModel.getAllSortedArtist(POPULARITY, list)
            }

            chipRateSing.setOnAvoidDuplicateClick {
                viewModel.getAllSortedArtist(SING, list)
            }

            chipRateDance.setOnAvoidDuplicateClick {
                viewModel.getAllSortedArtist(DANCE, list)
            }

            chipRatePerformance.setOnAvoidDuplicateClick {
                viewModel.getAllSortedArtist(PERFORMACE, list)
            }
        }
    }

    /**
     * Observe sorted artist rate : 정렬된 리스트 정보를 관찰하는 메서드
     *
     * 정렬된 리스트 정보를 바탕으로 BarGraph와 RecyclerView를 갱신시켜 준다.
     *
     * @author진혁
     */
    fun observeSortedArtistRate() {
        viewModel.getAllSortedArtist.observe(viewLifecycleOwner) { sortedList ->
            if (sortedList != null) { // 정렬된 리스트
                initBarGraph()
                setBarGraph(sortedList, viewModel.rateReferecne) // bar graph 세팅
                initRecycler(sortedList)  // recycleview 세팅
            }
        }
    }

    /**
     * Init bar graph : 기본 bar graph 세팅 메서드
     *
     *@author 진혁
     */
    private fun initBarGraph() {
        with(binding) {
            barChartRateCompare.run {
                description.isEnabled = true
                setMaxVisibleValueCount(10)
                setPinchZoom(false)
                setDrawBarShadow(false)
                setDrawGridBackground(false)

                axisLeft.run {
                    axisMaximum = 5f
                    axisMinimum = 0f
                    granularity = 0.1f
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
        }
    }

    /**
     * Set bar graph : 정렬된 리스트 정보와 기준 정보를 바탕으로 Graph에 그려질 Bar를 생성하는 메서드
     *
     * BarEntry를 생성하는 부분은 Dispatcher.Default 에서 동작한다.
     *
     * BarEntry의 색상과 크기를 설정하는 부분은 Main에서 동작한다.
     *
     * @param list : 정렬된 리스트
     * @param reference : 정렬 기준
     * @author 진혁
     */
    private fun setBarGraph(list: List<Artist>, reference: Referecne) {
        with(binding) {
            lifecycleScope.launch {
                barChartRateCompare.xAxis.valueFormatter = MyXAxisFormatter(list)

                val set: BarDataSet = withContext(defaultDispatcher) { // BarEntry 생성(default)
                    setBarGraphData(list, reference)
                }

                set.setColors( // 색상, 크기 설정(main)
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

    /**
     * Init recycler : recyclerView를 초기화 하는 메서드
     *
     * @param result : 정렬된 리스트
     * @author 진혁
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun initRecycler(result: List<Artist>) {
        with(binding) {
            val rateAdapter = RateAdapter(result) {
                viewModel.getArtistById(it.artistId)
                layoutRateMain.changeFragment(this@RateFragment, ArtistDetailFragment())
            }

            recyclerRateRanking.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = rateAdapter
            }
        }
    }

    /**
     * Set bar graph data : BarEntry를 생성하는 메서드
     *
     * @param list : 정렬된 리스트
     * @param reference : 기준
     * @return BarDataSet
     * @author 진혁
     */
    private fun setBarGraphData(list: List<Artist>, reference: Referecne): BarDataSet {
        val entries = arrayListOf<BarEntry>()
        when (reference) {
            AVERAGE -> {
                for ((idx, value) in list.withIndex()) {
                    if (value.rate != null)
                        entries.add(BarEntry((idx + 1) + 0.2f, value.rate!!.average))
                }
            }

            INCOME -> {
                for ((idx, value) in list.withIndex()) {
                    if (value.rate != null)
                        entries.add(BarEntry((idx + 1) + 0.2f, value.rate!!.income.toFloat()))
                }
            }

            POPULARITY -> {
                for ((idx, value) in list.withIndex()) {
                    if (value.rate != null)
                        entries.add(BarEntry((idx + 1) + 0.2f, value.rate!!.popularity.toFloat()))
                }
            }

            SING -> {
                for ((idx, value) in list.withIndex()) {
                    if (value.rate != null)
                        entries.add(BarEntry((idx + 1) + 0.2f, value.rate!!.sing.toFloat()))
                }
            }

            DANCE -> {
                for ((idx, value) in list.withIndex()) {
                    if (value.rate != null)
                        entries.add(BarEntry((idx + 1) + 0.2f, value.rate!!.dance.toFloat()))
                }
            }

            PERFORMACE -> {
                for ((idx, value) in list.withIndex()) {
                    if (value.rate != null)
                        entries.add(BarEntry((idx + 1) + 0.2f, value.rate!!.performance.toFloat()))
                }
            }

            else -> {
            }
        }

        return BarDataSet(entries, "DataSet") // 데이터셋 초기화
    }

    /**
     * My x axis formatter : Graph의 x축 설정 메서드
     *
     * @property artistList : 정렬된 리스트
     * @constructor Create empty My x axis formatter
     * @author 진혁
     */

    inner class MyXAxisFormatter(val artistList: List<Artist>) : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return (artistList.getOrNull(value.toInt() - 1)?.name ?: value.toString())
        }
    }
}