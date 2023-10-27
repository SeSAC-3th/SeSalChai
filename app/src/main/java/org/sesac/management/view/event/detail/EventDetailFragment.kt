package org.sesac.management.view.event.detail

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.launch
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Artist
import org.sesac.management.data.local.Event
import org.sesac.management.data.local.Manager
import org.sesac.management.data.model.DialogItem
import org.sesac.management.databinding.FragmentEventDetailBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.view.adapter.EventSelectArtistViewPagerAdapter
import org.sesac.management.view.artist.ArtistViewModel
import org.sesac.management.view.artist.detail.ArtistDetailFragment
import org.sesac.management.view.event.EventViewModel
import org.sesac.management.view.event.dialog.ArtistAddDialogFragment
import org.sesac.management.view.event.dialog.DialogDataListener
import org.sesac.management.view.event.edit.EventEditFragment

/**
 * Event detail fragment
 *
 * @constructor Create empty Event detail fragment
 * @author 혜원
 */
class EventDetailFragment
    : BaseFragment<FragmentEventDetailBinding>(FragmentEventDetailBinding::inflate),
    DialogDataListener {
    val TAG: String = "로그"
    private lateinit var viewPager: ViewPager2
    private var bannerPosition = 0
    private val eventViewModel: EventViewModel by activityViewModels()
    private val artistViewModel: ArtistViewModel by activityViewModels()
    private lateinit var artistIdList: List<Int>
    private var eventId = 0
    private var artists: List<Artist> = listOf()

    /* event 데이터 가져오기 */
    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                eventViewModel.eventDetail.collect { event ->
                    eventId = event.eventId
                    getEventDetail(event)
                    eventViewModel.getArtistFromEvent(eventId)
                }
            }
        }
    }

    /* 가져온 event 데이터 중 eventId 값을 이용하여 manager에서 일치하는 eventId의 artistId를 가져오기 */
    private fun observerSetup() {
        eventViewModel.getArtistFromEvent.observe(viewLifecycleOwner) { artist ->
            artist?.forEach {
                getSelectArtist(artist)
            }
        }
    }

//    private fun updateUI(event: Event) {
//        with(binding) {
//            ivEvent.let {
//                ivEvent.setImageBitmap(event.imgUri)
//            }
//            tvEventTitle.text = event.name
//            tvEventTime.text = event.date.toString()
//            tvEventPlace.text = event.place
//            tvEventDescription.text = event.description
//        }
//    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated() {
        with(binding) {
            tbEvent.setToolbarMenu("행사 상세", true) {
                eventDetailLayout.changeFragment(this@EventDetailFragment, EventEditFragment())
            }
        }
        observeData()
        observerSetup()
    }

    /* 가져온 event에 대한 정보를 binding */
    private fun getEventDetail(event: Event) {
        with(binding) {
            //행사 정보
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvEventTitle.text = event.name
                tvEventTime.text = "일시 : ${SimpleDateFormat("yyyy-MM-dd").format(event.date)}"
                tvEventPlace.text = "장소 : ${event.place}"
                tvEventDescription.text = "설명 : ${event.description}"
            }
            //이미지
            event.imgUri?.let {
                ivEvent.setImageBitmap(it)
            } ?: ivEvent.setImageResource(R.drawable.girls_generation_hyoyeon)

            /* 참여 아티스트 */
            with(tvEventArtistTitle) {
                setOnAvoidDuplicateClick {
                    val addDialog = ArtistAddDialogFragment()
                    addDialog.onDialogDataSelected(this@EventDetailFragment)
                    addDialog.show(childFragmentManager, "artistDialogFragment")
                }
            }
        }
    }

    /* 참여 아티스트 목록을 조회하는 메서드 */
    private fun getSelectArtist(artist: List<Artist>) {
        /* viewPager2 */
        with(binding) {
            viewPager = vpArtist
            Log.d(TAG, "getSelectArtist: 어댑터 값 $artist")
            val adapter = EventSelectArtistViewPagerAdapter(artist, onClick = {
                artistViewModel.getArtistById(it)
                binding.eventDetailLayout.changeFragment(
                    this@EventDetailFragment,
                    ArtistDetailFragment()
                )
            }).apply {
                notifyDataSetChanged()
            }
            viewPager.adapter = adapter
            viewPager = initialiseViewPager()

            bannerPosition = Int.MAX_VALUE / 2 - Math.ceil(artists.size.toDouble() / 2)
                .toInt() // viewPager의 position을 설정
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

    /**
     * ArtistAddDialogFragment로 부터 넘겨온 Artist 목록 List를 받아
     * Manager에 등록한다.
     * @param checkedList
     * @author 혜원
     */
    override fun onDialogDataSelected(checkedList: MutableList<DialogItem>) {
        artistIdList = checkedList.map { it.artistId }
        // artistIdListFlow에 새 값을 제공
        artistIdList.forEach {
            eventViewModel.insertManager(Manager(it, eventId))
        }
        Log.d(TAG, "EventDetailFragment - $artistIdList")
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
    }
}