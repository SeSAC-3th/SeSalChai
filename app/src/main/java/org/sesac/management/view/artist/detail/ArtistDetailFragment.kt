package org.sesac.management.view.artist.detail

import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Artist
import org.sesac.management.databinding.FragmentArtistDetailBinding
import org.sesac.management.view.adapter.ArtistEventViewPagerAdapter
import org.sesac.management.view.artist.ArtistViewModel
import org.sesac.management.view.artist.bottomsheet.RateBottomSheet

class ArtistDetailFragment :
    BaseFragment<FragmentArtistDetailBinding>(FragmentArtistDetailBinding::inflate) {
    private val viewModel: ArtistViewModel by viewModels()
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
        observeData()
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

    private fun observeData() {
        viewModel.getArtistDetail.observe(viewLifecycleOwner) { artist ->
            getViewToData(artist)
        }
    }

    private fun getViewToData(artist: Artist) {
        val memberInfo = convertMemberInfo(artist.memberInfo)
        with(binding) {
            tvArtist.text = "${artist.name}\n${artist.debutDay}\n${memberInfo.size}"
            ivArtist.setImageResource(R.drawable.girls_generation_hyoyeon)
            memberInfo.forEach {
                tvMember.text = "${binding.tvMember.text}\n$it"
            }
        }
    }

    private fun convertMemberInfo(memberInfo: String): List<String> {
        val delimiter = "," // 구분자, 여기서는 쉼표
        val stringList = memberInfo.split(delimiter)
        val resultList = stringList.map { it.trim() }
        return resultList
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