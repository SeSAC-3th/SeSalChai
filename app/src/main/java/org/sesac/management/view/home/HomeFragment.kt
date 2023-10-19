package org.sesac.management.view.home

import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentHomeBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.view.notice.NoticeFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun onViewCreated() {
        with(binding) {
            tvHomeCompName.text = resources.getString(R.string.title_home_comp_name)
            tvHomeCompInfo.text = resources.getString(R.string.title_home_comp_info)
            includedLayoutHomeNotice.ivNoticeNavigate.setOnAvoidDuplicateClick {
                homeLayout.changeFragment(this@HomeFragment, NoticeFragment())
            }
        }
    }
}