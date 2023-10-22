package org.sesac.management.view.home

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.data.room.Notice
import org.sesac.management.databinding.FragmentHomeBinding
import org.sesac.management.util.extension.changeFragment
import org.sesac.management.view.notice.NoticeDetailFragment
import org.sesac.management.view.notice.NoticeFragment
import org.sesac.management.view.notice.NoticeRecyclerAdapter
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

        with(binding.includedLayoutTextinput){
            tilLayout.hint=resources.getString(R.string.app_name)
            tilLayout.isCounterEnabled = true
            tilLayout.counterMaxLength = 10
            tilLayout.startIconDrawable = resources.getDrawable(R.drawable.baseline_menu_24)
            tilEt.maxLines = 2
            tilLayout.helperText=resources.getString(R.string.app_name)
        }
        with(binding.includedLayoutTextinputPassword){
            tilLayout.isEndIconVisible = true
            tilLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        }
    }
    fun observeSetUp() {
        noticeViewModel.getHomeNotice()?.observe(
            viewLifecycleOwner) {notices ->
            notices?.let {
                updateUI(notices)
            }
        }
    }
    private fun updateUI(notices: List<Notice>) {
            noticeAdapter= HomeNoticeAdapter(notices)
            binding.includedLayoutHomeNotice.rvNotice.adapter=noticeAdapter
        }

}