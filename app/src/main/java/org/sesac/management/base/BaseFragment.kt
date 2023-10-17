package org.sesac.management.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sesac.management.util.common.CLICK_INTERVAL_TIME
import org.sesac.management.util.common.FragmentInflate
import org.sesac.management.util.common.INPUT_COMPLETE_TIME
import org.sesac.management.util.common.RXERROR
import reactivecircus.flowbinding.activity.backPresses
import java.util.concurrent.TimeUnit


abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: FragmentInflate<VB>,
) : Fragment() {

    var _binding: VB? = null
    val binding get() = _binding!!

//    private lateinit var backPressCallback: OnBackPressedCallback // 뒤로 가기 이벤트 콜백
    protected open fun savedInstanceStateOnCreateView() {} // 필요하면 재정의
    protected open fun savedInstanceStateOnViewCreated() {} // 필요하면 재정의
    protected open fun onCreateView() {} // 필요하면 재정의
    protected abstract fun onViewCreated() // 반드시 재정의

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        if (savedInstanceState == null) { // onSaveInstanceState로 데이터를 넘긴 것이 있다면 null이 아니므로 작동X -> onSaveInstanceState 전에 한번만 호출되었으면 하는 것
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
//        backPressCallback.remove() // 뒤로 가기 이벤트 콜백 해제!
        compositeDisposable.dispose() // compositeDisposable 해제
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
        // FlowBinding의 backPresses 확장함수를 활용하는 방법
        requireActivity().onBackPressedDispatcher.backPresses(viewLifecycleOwner)
            .onEach {
                if (parentFragmentManager.backStackEntryCount > 0) {
                    parentFragmentManager.popBackStackImmediate(null, 0)
                } else {
                    requireActivity().finish()
                }
            }.launchIn(CoroutineScope(Dispatchers.Main))

        // 단순히 backPressCallback을 활용한 방법
//        backPressCallback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                if (parentFragmentManager.backStackEntryCount > 0) {
//                    parentFragmentManager.popBackStackImmediate(null, 0)
//                } else {
//                    requireActivity().finish()
//                }
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressCallback)
    }

    /**
     * @author 진혁
     * On avoid duplicate click, view에 대한 중복 클릭 방지 이벤트 처리 메서드
     * throttleFrist() 안에 sleep 타임은 0.3초로 설정되어 있음, 0.3초간 클릭 못함
     * @param actionInMainThread : main 쓰레드에서 처리될 이벤트
     * @receiver 모든 view
     */
    fun View.setOnAvoidDuplicateClick(actionInMainThread: () -> Unit) {
        compositeDisposable //
            .add(
                this.clicks()
                    .observeOn(Schedulers.io()) // 이후 chain의 메서드들은 쓰레드 io 영역에서 처리
                    .throttleFirst(CLICK_INTERVAL_TIME, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread()) // 이후 chain의 메서드들은 쓰레드 main 영역에서 처리
                    .subscribe(
                        {
                            actionInMainThread()
                        }, {
                            Log.e(RXERROR, it.toString())
                        }
                    )
            )
    }

    /**
     * @author 진혁
     * Set on finish input, textview 입력 완성 후 이벤트 처리 메서드
     * Rx의 debounce() 안에 sleep 타임은 1초로 설정되어 있음, 1초 뒤에야 현재의 text를 반환 받을 수 있다.
     * RxBinding으로 구현
     * @param actionInMainThread : main 쓰레드에서 처리될 이벤트
     * @receiver 모든 textview
     */
    fun TextView.setOnFinishInput(actionInMainThread: (completedText: String) -> Unit) {
        compositeDisposable
            .add(
                this.textChanges()
                    .observeOn(Schedulers.io())
                    .debounce(INPUT_COMPLETE_TIME, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            actionInMainThread(it.toString())
                        }, {
                            Log.e(RXERROR, it.toString())
                        }
                    )
            )
    }


}