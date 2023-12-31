package org.sesac.management.view.artist

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.local.Artist
import org.sesac.management.data.local.ArtistType
import org.sesac.management.databinding.FragmentArtistBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.util.extension.setOnFinishInputFlow
import org.sesac.management.view.adapter.recyclerview.ArtistRecyclerAdapter
import org.sesac.management.view.artist.detail.ArtistDetailFragment
import org.sesac.management.view.artist.enroll.ArtistEnrollFragment

/**
 * Artist fragment
 *
 * @constructor Create empty Artist fragment
 * @author 우빈
 */
class ArtistFragment : BaseFragment<FragmentArtistBinding>(FragmentArtistBinding::inflate) {
    private val viewModel: ArtistViewModel by activityViewModels()
    private lateinit var artistAdapter: ArtistRecyclerAdapter

    override fun onViewCreated() {
        getArtistInfo()
        observeData()
        initView()
    }

    /**
     * 아티스트에 대한 정보 불러옴
     * @author 우빈
     */
    private fun getArtistInfo() {
        viewModel.getAllArtist()
    }

    private fun observeData() {
        viewModel.getAllArtist.observe(viewLifecycleOwner) { artist ->
            if (artist != null) makeList(artist)
        }
    }

    /**
     * Make list
     *
     * @param artistList
     * @author 민서
     */
    private fun makeList(artistList: List<Artist>) {
        with(binding.rvArtist) {
            layoutManager = GridLayoutManager(activity, 2)
            artistAdapter = ArtistRecyclerAdapter(
                artistList,
                onDelete = {
                    AlertDialog.Builder(requireContext()).run {
                        setMessage("정말로 삭제하시겠습니까?")
                        setNegativeButton("cancel", null)
                        setPositiveButton(
                            "ok",
                        ) { _, _ ->
                            viewModel.deleteArtist(it)
                        }
                        create()
                        show()
                    }
                },
                onClick = {
                    viewModel.getArtistById(it)
                    viewModel.getEventFromArtistId(it)
                    childFragmentManager
                        .beginTransaction()
                        .add(binding.artistLayout.id, ArtistDetailFragment())
                        .addToBackStack(null)
                        .commitAllowingStateLoss()
                }
            )
            adapter = artistAdapter
            artistAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun initView() {
        with(binding) {
            /* chip Button : 가수 목록 */
            chipAll.setOnAvoidDuplicateClick {
                viewModel.getAllArtist()
            }
            /* chip Button : 가수 목록 */
            chipSinger.setOnAvoidDuplicateClick {
                viewModel.getArtistByType(ArtistType.SINGER)
            }

            /* chip Button : 배우 목록 */
            chipActor.setOnAvoidDuplicateClick {
                viewModel.getArtistByType(ArtistType.ACTOR)
            }

            /* chip Button : 코미디언 목록 */
            chipComedian.setOnAvoidDuplicateClick {
                viewModel.getArtistByType(ArtistType.COMEDIAN)
            }

            /* chip Button : 모델 목록 */
            chipModel.setOnAvoidDuplicateClick {
                viewModel.getArtistByType(ArtistType.MODEL)
            }

            /* Floating Button : 아티스트 등록 */
            btnArtistEnroll.setOnAvoidDuplicateClick {
                artistLayout.changeFragment(this@ArtistFragment, ArtistEnrollFragment())
            }

            with(tbArtist) {
                etSearch.setOnFinishInputFlow {
                    if (it.isNotEmpty()) {
                        viewModel.getSearchResult(it)
                    } else {
                        viewModel.getAllArtist()
                    }
                }
            }

            /**
             * 아티스트에 대한 정보 새로고침
             * @author 우빈
             */
            with(swipeRefresh) {
                setSize(SwipeRefreshLayout.MEASURED_STATE_TOO_SMALL)
                setColorSchemeColors(
                    android.R.color.holo_blue_bright,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_green_light,
                    android.R.color.holo_red_light
                )
                setOnRefreshListener {
                    Handler(Looper.getMainLooper()).postDelayed({
                        artistAdapter.let {
                            viewModel.getAllArtist()
                            it.notifyItemRangeChanged(0, childCount)
                        }
                        isRefreshing = false
                    }, 1000L)
                }
            }
        }
    }
}