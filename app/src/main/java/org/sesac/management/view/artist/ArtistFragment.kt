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
            /* chip Button : 가수 목록 */
            chipSinger.setOnAvoidDuplicateClick {
                // 임시로 넣어 둔 코드(화면 전환 코드)
                artistLayout.changeFragment(this@ArtistFragment, ArtistDetailFragment())
            }

            /* chip Button : 배우 목록 */
            chipActor.setOnAvoidDuplicateClick {
                artistLayout.changeFragment(this@ArtistFragment, ArtistEnrollFragment())
            }

            /* chip Button : 코미디언 목록 */
            chipComedian.setOnAvoidDuplicateClick {
                artistLayout.changeFragment(this@ArtistFragment, ArtistEnrollFragment())
            }

            /* chip Button : 모델 목록 */
            chipModel.setOnAvoidDuplicateClick {
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