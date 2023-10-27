package org.sesac.management.view.home

import android.graphics.Rect
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Artist
import org.sesac.management.data.local.Notice
import org.sesac.management.databinding.FragmentHomeBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.view.adapter.recyclerview.HomeArtistAdapter
import org.sesac.management.view.adapter.recyclerview.HomeNoticeAdapter
import org.sesac.management.view.artist.ArtistViewModel
import org.sesac.management.view.notice.NoticeFragment
import org.sesac.management.view.notice.NoticeViewModel

/**
 * Home fragment
 *
 * @constructor Create empty Home fragment
 * @author 우빈, 종혁
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val artistViewModel: ArtistViewModel by activityViewModels()
    private val noticeViewModel: NoticeViewModel by activityViewModels()

    private var artistAdapter = HomeArtistAdapter(emptyList())
    private var noticeAdapter = HomeNoticeAdapter(emptyList())

    override fun onViewCreated() {
        with(binding) {
            tvHomeCompName.text = resources.getString(R.string.title_home_comp_name)
            tvHomeCompInfo.text = resources.getString(R.string.title_home_comp_info)
            includedLayoutHomeNotice.ivNoticeNavigate.setOnAvoidDuplicateClick {
                homeLayout.changeFragment(this@HomeFragment, NoticeFragment())
            }

            includedLayoutHomeArtist.rvHomeArtist.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL, false
                )
                addItemDecoration(
                   SpaceItemDecoration(8),
                    LinearLayoutManager.HORIZONTAL
                )
            }

            includedLayoutHomeNotice.rvNotice.apply {
                layoutManager =
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                addItemDecoration(
                   SpaceItemDecoration(8),
                    LinearLayoutManager.HORIZONTAL
                )
            }
            observeSetUp()
        }
    }

    private fun observeSetUp() {
        noticeViewModel.getHomeNotice()?.observe(
            viewLifecycleOwner
        ) { notices ->
            notices?.let {
                updateNoticeUI(notices)
            }
        }

        artistViewModel.getAllArtist.observe(
            viewLifecycleOwner
        ) { artists ->
            artists?.let {
                updateArtistUI(artists)
            }
        }
    }

    private fun updateArtistUI(artists: List<Artist>) {
        artistAdapter = HomeArtistAdapter((artists))
        binding.includedLayoutHomeArtist.rvHomeArtist.adapter = artistAdapter
    }

    private fun updateNoticeUI(notices: List<Notice>) {
        noticeAdapter = HomeNoticeAdapter(notices)
        binding.includedLayoutHomeNotice.rvNotice.adapter = noticeAdapter
    }

    /**
     * Space item decoration
     *
     * @property space
     * @constructor Create empty Space item decoration
     * @author 종혁
     */
    inner class SpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.left = space.times(2)
            outRect.right = space.times(2)
            outRect.top=space
            outRect.bottom=space
        }
    }
}