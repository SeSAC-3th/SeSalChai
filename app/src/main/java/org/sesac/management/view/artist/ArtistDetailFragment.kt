package org.sesac.management.view.artist

import androidx.viewpager2.widget.ViewPager2
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentArtistDetailBinding

class ArtistDetailFragment :
    BaseFragment<FragmentArtistDetailBinding>(FragmentArtistDetailBinding::inflate) {
    private lateinit var viewPager: ViewPager2
    private var bannerPosition = 0

    /* events 임시 데이터 */
    private val events = listOf(
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background
    )

    override fun onViewCreated() {
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