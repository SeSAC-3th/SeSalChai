package org.sesac.management.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import org.sesac.management.util.common.FragmentInflate


abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: FragmentInflate<VB>,
) : Fragment() {

    var _binding: VB? = null
    val binding get() = _binding!!

    private lateinit var backPressCallback: OnBackPressedCallback // 뒤로 가기 이벤트 콜백
    protected open fun savedInstanceStateOnCreateView() {} // 필요하면 재정의
    protected open fun savedInstanceStateOnViewCreated() {} // 필요하면 재정의
    protected open fun onCreateView() {} // 필요하면 재정의
    protected abstract fun onViewCreated() // 반드시 재정의

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        if (savedInstanceState == null) {
            savedInstanceStateOnCreateView()
        }
        initBackPressCallback()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        backPressCallback.remove() // 뒤로 가기 이벤트 콜백 해제!
    }

    /**
     * @author 진혁
     * Init back press callback, 뒤로가기 이벤트 초기화 메서드
     * : 해당 fragment를 소유하고 있는 부모 fragment의 stack에 fragment가 있다면 그것을 pop한다.
     * -> 현재 fragment로 전환할 때 addToBackStack으로 부모 fragment의 stack에 현재 fragment를 넣었다면 무조건 현재 fragment가 pop된다.
     * 만약 addToBackStack을 하지 않았다면 현재 fragment에서 뒤로가기가 작동하지 않는다.
     * 부모 fragment의 stack이 비었다면 그것은 Activity에 붙어 있는 가장 첫번째 fragment이므로 앱을 종료한다.
     */
    private fun initBackPressCallback() {
        backPressCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (parentFragmentManager.backStackEntryCount > 0) {
                    parentFragmentManager.popBackStackImmediate(null, 0)
                } else {
                    requireActivity().finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressCallback)
    }
}