package org.sesac.management.view.home

import android.text.Editable
import android.view.View
import com.google.android.material.textfield.TextInputLayout
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

        val editText2 = binding.includedLayoutTextinputPassword.tilEt
        addTextWatcherToTextInputEditText(editText2)

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

    override fun afterTextChange(s: Editable?) {
        if (s.toString().isEmpty()) {
            binding.includedLayoutTextinput.tilLayout.error = "ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ"
        }
    }
}