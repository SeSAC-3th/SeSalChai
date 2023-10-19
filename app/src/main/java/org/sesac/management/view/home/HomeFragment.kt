package org.sesac.management.view.home

import android.text.Editable
import android.view.View
import org.sesac.management.R
import org.sesac.management.base.BaseFragment
import org.sesac.management.databinding.FragmentHomeBinding
import org.sesac.management.view.notice.NoticeFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun onViewCreated() {
        binding.tvHomeCompName.text = resources.getString(R.string.title_home_comp_name)
        binding.tvHomeCompInfo.text = resources.getString(R.string.title_home_comp_info)
        binding.includedLayoutHomeNotice.ivNoticeNavigate.setOnClickListener {
            binding.appbarHome.visibility = View.GONE
            childFragmentManager
                .beginTransaction()
                .add(binding.homeLayout.id, NoticeFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        val editText = binding.includedLayoutTextinput.tilEt
        addTextWatcherToTextInputEditText(editText)
    }

    override fun afterTextChange(s: Editable?) {
        if (s.toString().isEmpty()){
            binding.includedLayoutTextinput.tilLayout.error = "공백은 허용하지 않습니다."
        }
    }
}