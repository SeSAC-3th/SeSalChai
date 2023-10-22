package org.sesac.management.view.home

import android.text.Editable
import android.view.View
import com.google.android.material.textfield.TextInputLayout
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
}