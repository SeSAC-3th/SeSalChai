package org.sesac.management.view.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentArtistDetailBinding

class ArtistDetailFragment : BaseFragment<FragmentArtistDetailBinding>(FragmentArtistDetailBinding::inflate) {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentArtistDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            /* toolbar 아이콘, 텍스트 설정 */
            layoutToolbar.ivBack.setImageResource(R.drawable.baseline_arrow_back_24)
            layoutToolbar.tvTitle.text = "아티스트"
            layoutToolbar.ivHamburger.setImageResource(R.drawable.baseline_menu_24)

            layoutToolbar.ivBack.setOnClickListener {
                childFragmentManager
                    .beginTransaction()
                    .add(artistDetailLayout.id, ArtistFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
//            layoutToolbar.ivHamburger.setOnClickListener {
//                childFragmentManager
//                    .beginTransaction()
//                    .addToBackStack(null)
//                    .commitAllowingStateLoss()
//            }

            /* viewPager2 */
            viewPager = vpSchedule
            viewPager = initialiseViewPager()
            bannerPosition = Int.MAX_VALUE / 2 - Math.ceil(events.size.toDouble() / 2).toInt()
            viewPager.setCurrentItem(0, false)
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) { }

                //사용자가 스크롤 했을때 position 수정
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    bannerPosition = position
                }

                override fun onPageScrollStateChanged(state: Int) { }
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