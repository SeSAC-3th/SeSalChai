package org.sesac.management.view.artist.detail

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Artist
import org.sesac.management.data.local.Event
import org.sesac.management.databinding.FragmentArtistDetailBinding
import org.sesac.management.util.common.ARTIST
import org.sesac.management.view.MainActivity
import org.sesac.management.view.adapter.ArtistEventViewPagerAdapter
import org.sesac.management.view.artist.ArtistViewModel
import org.sesac.management.view.artist.bottomsheet.RateBottomSheet

class ArtistDetailFragment :
    BaseFragment<FragmentArtistDetailBinding>(FragmentArtistDetailBinding::inflate) {
    private val viewModel: ArtistViewModel by activityViewModels()
    private lateinit var viewPager: ViewPager2
    private var bannerPosition = 0

    /* events 임시 데이터 */
    private var events: List<Event> = listOf()

    override fun onViewCreated() {
        observeData()
        initView()
    }

    private fun observeData() {
        Log.d(ARTIST, "2. getArtistById: ${viewModel.getArtistDetail}")

        viewModel.getArtistDetail.observe(viewLifecycleOwner) { artist ->
            Log.d(ARTIST, "3.getArtistById: ${artist}")
            getViewToData(artist)
        }
    }

    private fun getViewToData(artist: Artist) {
        Log.d(ARTIST, "4. getArtistById: ${artist}")

        val memberInfo = convertMemberInfo(artist.memberInfo)
        with(binding) {
            //아티스트 정보
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvArtist.text =
                    "이름:${artist.name}\n 데뷔 일: ${SimpleDateFormat("yyyy-MM-dd").format(artist.debutDay)}\n 인원 수:${memberInfo.size}명"
            }

            //이미지
            artist.imgUri?.let {
                ivArtist.setImageBitmap(it)
            } ?: ivArtist.setImageResource(R.drawable.girls_generation_hyoyeon)

            //멤버 정보
            tvMember.text = ""
            memberInfo.forEach {
                if(tvMember.text.isNotEmpty()){
                    tvMember.text = "${binding.tvMember.text}\n$it"
                }else{
                    tvMember.text = it
                }
            }
            events = viewModel.getEventFromArtist.value ?: listOf()

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

            /* Bottom Sheet show*/
            ivChart.setOnAvoidDuplicateClick {
                val rateBottomSheet = RateBottomSheet()
                activity?.let { rateBottomSheet.show(it.supportFragmentManager, "dialog") }
            }

            /* viewPager2 */
            viewPager = vpSchedule
            viewPager = initialiseViewPager()
            bannerPosition = Int.MAX_VALUE / 2 - Math.ceil(events.size.toDouble() / 2).toInt()
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
    }

    /* viewpager2 adapter 연결 및 margin 설정 */
    private fun initialiseViewPager() = viewPager.apply {
        /* 여백, 너비에 대한 정의 */
        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.pageWidth)
        val screenWidth = resources.displayMetrics.widthPixels
        val offsetPx = screenWidth - pageMarginPx - pagerWidth
        viewPager.offscreenPageLimit = 3

        viewPager.setPageTransformer { page, position ->
            page.translationX = position * -offsetPx
        }

        adapter = ArtistEventViewPagerAdapter(events).apply {
            notifyDataSetChanged()
        }
    }

}