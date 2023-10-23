package org.sesac.management.view.home

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.room.Notice
import org.sesac.management.databinding.FragmentHomeBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.view.adapter.recyclerview.HomeNoticeAdapter
import org.sesac.management.view.notice.NoticeFragment
import org.sesac.management.view.notice.NoticeViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val noticeViewModel: NoticeViewModel by viewModels()
    private var noticeAdapter = HomeNoticeAdapter(emptyList())
    override fun onViewCreated() {
        with(binding) {
            tvHomeCompName.text = resources.getString(R.string.title_home_comp_name)
            tvHomeCompInfo.text = resources.getString(R.string.title_home_comp_info)
            includedLayoutHomeNotice.ivNoticeNavigate.setOnAvoidDuplicateClick {
                homeLayout.changeFragment(this@HomeFragment, NoticeFragment())
            }

            includedLayoutHomeNotice.rvNotice.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                observeSetUp()
            }
        }
    }

    fun observeSetUp() {
        noticeViewModel.getHomeNotice()?.observe(
            viewLifecycleOwner
        ) { notices ->
            notices?.let {
                updateUI(notices)
            }
        }
    }

    private fun updateUI(notices: List<Notice>) {
        noticeAdapter = HomeNoticeAdapter(notices)
        binding.includedLayoutHomeNotice.rvNotice.adapter = noticeAdapter
    }

}