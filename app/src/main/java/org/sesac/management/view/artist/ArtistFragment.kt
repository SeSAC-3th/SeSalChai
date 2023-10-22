package org.sesac.management.view.artist

import androidx.recyclerview.widget.GridLayoutManager
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.model.artistList
import org.sesac.management.databinding.FragmentArtistBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.view.artist.detail.ArtistDetailFragment
import org.sesac.management.view.artist.enroll.ArtistEnrollFragment

class ArtistFragment : BaseFragment<FragmentArtistBinding>(FragmentArtistBinding::inflate) {

    override fun onViewCreated() {
        with(binding) {
            /* chip Button : 그룹명 순 정렬 */
            chipGroupSort.setOnAvoidDuplicateClick {
                // 임시로 넣어 둔 코드(화면 전환 코드)
                artistLayout.changeFragment(this@ArtistFragment, ArtistDetailFragment())
            }

            /* chip Button : 데뷔일 순 정렬 */
            chipDebutSort.setOnAvoidDuplicateClick {
                artistLayout.changeFragment(this@ArtistFragment, ArtistEnrollFragment())
            }
            /* Floating Button : 아티스트 등록 */
            btnArtistEnroll.setOnAvoidDuplicateClick {
                artistLayout.changeFragment(this@ArtistFragment, ArtistEnrollFragment())
            }
            /* Artist RecyclerView */
            with(rvArtist) {
                layoutManager = GridLayoutManager(activity, 2)
                adapter = ArtistRecyclerAdapter(
                    artistList,
                    onClick={
                        childFragmentManager
                            .beginTransaction()
                            .add(binding.artistLayout.id, ArtistDetailFragment())
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
                    }
                )
            }
        }
    }

}