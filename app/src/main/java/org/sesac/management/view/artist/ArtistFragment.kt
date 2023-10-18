package org.sesac.management.view.artist

import androidx.recyclerview.widget.GridLayoutManager
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.model.artistList
import org.sesac.management.databinding.FragmentArtistBinding

class ArtistFragment : BaseFragment<FragmentArtistBinding>(FragmentArtistBinding::inflate) {

    override fun onViewCreated() {
        with(binding) {
            /* chip Button : 그룹명 순 정렬 */
            with(chipGroupSort) {
                setOnClickListener {
                    childFragmentManager
                        .beginTransaction()
                        .add(binding.artistLayout.id, ArtistDetailFragment())
                        .addToBackStack(null)
                        .commitAllowingStateLoss()
                }
            }
            /* chip Button : 데뷔일 순 정렬 */
            with(chipDebutSort) {
                setOnClickListener {
                    childFragmentManager
                        .beginTransaction()
                        .add(binding.artistLayout.id, ArtistEnrollFragment())
                        .addToBackStack(null)
                        .commitAllowingStateLoss()
                }
            }
            /* Floating Button : 아티스트 등록 */
            with(btnArtistEnroll) {
                setOnClickListener {
                    childFragmentManager
                        .beginTransaction()
                        .add(binding.artistLayout.id, ArtistEnrollFragment())
                        .addToBackStack(null)
                        .commitAllowingStateLoss()
                }
            }
            /* Artist RecyclerView */
            with(rvArtist) {
                layoutManager = GridLayoutManager(activity, 2)
                adapter = ArtistRecyclerAdapter(
                    artistList,
                    childFragmentManager,
                    ArtistDetailFragment(),
                    R.id.artist_layout
                )
            }
        }
    }

}