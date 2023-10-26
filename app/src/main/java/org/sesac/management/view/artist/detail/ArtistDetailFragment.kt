package org.sesac.management.view.artist.detail

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Artist
import org.sesac.management.data.local.Event
import org.sesac.management.data.model.toModelArtist
import org.sesac.management.databinding.FragmentArtistDetailBinding
import org.sesac.management.view.adapter.ArtistEventViewPagerAdapter
import org.sesac.management.view.artist.ArtistViewModel
import org.sesac.management.view.artist.bottomsheet.RateBottomSheet
import org.sesac.management.view.artist.edit.ArtistEditFragment
import org.sesac.management.view.event.EventFragment

class ArtistDetailFragment :
    BaseFragment<FragmentArtistDetailBinding>(FragmentArtistDetailBinding::inflate) {
    private val viewModel: ArtistViewModel by activityViewModels()
    private lateinit var viewPager: ViewPager2
    private var bannerPosition = 0
    private var artistId = 0
    private lateinit var tempArtist: org.sesac.management.data.model.Artist
    private var rateId = 0

    override fun onViewCreated() {
        observeData()
        initView()
    }

    private fun observeData() {
        viewModel.getArtistDetail.observe(viewLifecycleOwner) { artist ->
            if (artist != null) {
                artistId = artist.artistId
                tempArtist = artist.toModelArtist()
                Log.e("tempArtist", tempArtist.toString())
                getViewToData(artist)
            }
        }
    }

    private fun getViewToData(artist: Artist) {
        val memberInfo = convertMemberInfo(artist.memberInfo)
        with(binding) {
            //아티스트 정보
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvArtist.text =
                    "이름:${artist.name}\n 데뷔 일: ${SimpleDateFormat("yyyy-MM-dd").format(artist.debutDay)}\n 인원 수:${memberInfo.size}명"
            }

            // 이미지
            artist.imgUri?.let {
                ivArtist.setImageBitmap(it)
            } ?: ivArtist.setImageResource(R.drawable.girls_generation_hyoyeon)

            // 멤버 정보
            tvMember.text = ""
            memberInfo.forEach {
                if (tvMember.text.isNotEmpty()) {
                    tvMember.text = "${binding.tvMember.text}\n$it"
                } else {
                    tvMember.text = it
                }
            }
            // 행사 정보
            observeEvents()

            // 평가 정보
            chartSettings(artist)
        }
    }

    private fun observeEvents() {
        viewModel.getEventFromArtist.observe(viewLifecycleOwner) {
            if (it != null) {
                initViewPager(it)
            }
        }
    }


    private fun convertMemberInfo(memberInfo: String): List<String> {
        val delimiter = "," // 구분자, 여기서는 쉼표
        val stringList = memberInfo.split(delimiter)
        val resultList = stringList.map { it.trim() }
        return resultList
    }

    private fun initView() {
        with(binding) {
            layoutToolbar.setToolbarMenu("아티스트 상세", true)
            ivArtistEdit.setOnClickListener {
                editArtist()
            }

            /* Bottom Sheet show*/
            radarChart.setOnAvoidDuplicateClick {
                val bundle = Bundle()
                bundle.putInt("artistId", tempArtist.artistId)
                bundle.putInt("rateId", rateId)
                val rateBottomSheet = RateBottomSheet()
                rateBottomSheet.arguments = bundle
                childFragmentManager.beginTransaction().add(rateBottomSheet, "Rate")
                    .commitAllowingStateLoss()
            }
        }
    }

    private fun editArtist() {
        val artistEditFragment = ArtistEditFragment()
        childFragmentManager.beginTransaction()
            .add(binding.artistDetailLayout.id, artistEditFragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    /**
     * MPAndroidChart Settings Method
     * rate 항목에 맞게 라벨을 추가하였습니다.
     */
    private fun chartSettings() {
        with(binding) {
            val labels = listOf(
                getString(R.string.rate_income),
                getString(R.string.rate_popularity),
                getString(R.string.rate_sing),
                getString(R.string.rate_dance),
                getString(R.string.rate_performance)
            )
            radarChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            radarChart.xAxis.labelCount = labels.size
            radarChart.yAxis.axisMaximum = 5f
            radarChart.yAxis.axisMinimum = 0f

            /* Chart 데이터 */
            val entries = ArrayList<RadarEntry>()
            entries.add(RadarEntry(5f))
            entries.add(RadarEntry(2f))
            entries.add(RadarEntry(3f))
            entries.add(RadarEntry(4f))
            entries.add(RadarEntry(5f))

    private fun initViewPager(data: List<Event>) {
        /* viewPager2 */
        viewPager = binding.vpSchedule
        viewPager = initialiseViewPager(data)
        bannerPosition = Int.MAX_VALUE / 2 - Math.ceil(data.size.toDouble() / 2).toInt()
        viewPager.setCurrentItem(0, false)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
            }

            //사용자가 스크롤 했을때 position 수정
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bannerPosition = position
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    /* viewpager2 adapter 연결 및 margin 설정 */
    private fun initialiseViewPager(data: List<Event>) = viewPager.apply {
        /* 여백, 너비에 대한 정의 */
        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.pageWidth)
        val screenWidth = resources.displayMetrics.widthPixels
        val offsetPx = screenWidth - pageMarginPx - pagerWidth
        viewPager.offscreenPageLimit = 3

        viewPager.setPageTransformer { page, position ->
            page.translationX = position * -offsetPx
        }

        adapter = ArtistEventViewPagerAdapter(data, onClick = {
            childFragmentManager
                .beginTransaction()
                //TODO: EventDetailFragment로 이동
                .add(binding.artistDetailLayout.id, EventFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }).apply {
            notifyDataSetChanged()
        }
    }

    /**
     * MPAndroidChart Settings Method
     * rate 항목에 맞게 라벨을 추가하였습니다.
     */
    private fun chartSettings(artist: Artist) {
        with(binding) {
            val labels = listOf(
                getString(R.string.rate_income),
                getString(R.string.rate_popularity),
                getString(R.string.rate_sing),
                getString(R.string.rate_dance),
                getString(R.string.rate_performance)
            )
            radarChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            radarChart.xAxis.labelCount = labels.size
            radarChart.yAxis.axisMaximum = 5f
            radarChart.yAxis.axisMinimum = 0f

            /* Chart 데이터 */
            val entries = ArrayList<RadarEntry>()
            if (artist.rate != null) {
                entries.add(RadarEntry(artist.rate?.income?.toFloat()!!))
                entries.add(RadarEntry(artist.rate?.popularity?.toFloat()!!))
                entries.add(RadarEntry(artist.rate?.sing?.toFloat()!!))
                entries.add(RadarEntry(artist.rate?.dance?.toFloat()!!))
                entries.add(RadarEntry(artist.rate?.performance?.toFloat()!!))
            }

            val dataSet = RadarDataSet(entries, "평가")
            dataSet.color = resources.getColor(R.color.primary1)
            dataSet.setDrawFilled(true)
            dataSet.fillColor = resources.getColor(R.color.primary2)

            val data = RadarData(dataSet)
            radarChart.data = data
            radarChart.description.isEnabled = false
            radarChart.yAxis.setDrawLabels(false)
            radarChart.setTouchEnabled(false)
        }
    }
}