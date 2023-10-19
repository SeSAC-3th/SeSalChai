package org.sesac.management.base

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.textfield.TextInputEditText
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.viewpager2.pageSelections
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sesac.management.R
import org.sesac.management.databinding.LayoutToolbarBinding
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
     * Init back press callback, 뒤로가기 이벤트 초기화 메서드
     *
     * : 해당 fragment를 소유하고 있는 부모 fragment의 stack에 fragment가 있다면 그것을 pop한다.
     *
     * -> 현재 fragment로 전환할 때 addToBackStack으로 부모 fragment의 stack에 현재 fragment를 넣었다면 무조건 현재 fragment가 pop된다.
     *
     * 만약 addToBackStack을 하지 않았다면 현재 fragment에서 뒤로가기가 작동하지 않는다.
     *
     * 부모 fragment의 stack이 비었다면 그것은 Activity에 붙어 있는 가장 첫번째 fragment이므로 앱을 종료한다.
     * @author 진혁
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
     * On avoid duplicate click, view에 대한 중복 클릭 방지 이벤트 처리 메서드
     *
     * throttleFrist() 안에 sleep 타임은 0.3초로 설정되어 있음, 0.3초간 클릭 못함
     *
     * @param actionInMainThread : main 쓰레드에서 처리될 이벤트
     * @receiver 모든 view
     * @author 진혁
     */
    fun View.setOnAvoidDuplicateClick(actionInMainThread: () -> Unit) {
        compositeDisposable
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
     * Set on finish input, textview 입력 완성 후 이벤트 처리 메서드
     *
     * Rx의 debounce() 안에 sleep 타임은 1초로 설정되어 있음, 1초 뒤에야 현재의 text를 반환 받을 수 있다.
     *
     * RxBinding으로 구현
     * @param actionInMainThread : main 쓰레드에서 처리될 이벤트
     * @receiver 모든 textview
     * @author 진혁
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

    fun ViewPager2.OnPageChangeCallback(actionInMainThread: () -> Unit) {
        compositeDisposable
            .add(
                this.pageSelections()
                    .observeOn(AndroidSchedulers.mainThread())
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
     * Set toolbar menu, toolbar 세팅 메서드
     *
     * setToolbarMenu(binding.toolbar, "제목") // 제목만
     *
     * setToolbarMenu(binding.toolbar, "제목", true) // 제목, back버튼
     *
     * setToolbarMenu(binding.toolbar, "제목", true){ 햄버거 클릭 이벤트 } // 제목, back버튼, hamburger버튼
     *
     * @param toolbar 해당 fragment에 포함된 toolbarlayout의 id
     * @param title toolbar의 제목
     * @param backBT  back 키 유무
     * @param hamburgerListener hamburger 버튼 클릭 이벤트
     * @author 진혁
     */
    fun setToolbarMenu(
        toolbar: LayoutToolbarBinding, // 툴바 레이아웃 id
        title: String, // 툴바 제목
        backBT: Boolean = false, // true 안해주면, 기본 false
        hamburgerListener: (() -> Unit)? = null, // hamburger 클릭 이벤트 처리, 기본 null
    ) {
        with(toolbar) {
            tvTitle.text = title // 툴바 제목은 무조건
            if (backBT) ivBack.setImageResource(R.drawable.baseline_arrow_back_24) // backBT이 있을 경우
            ivBack.setOnAvoidDuplicateClick { // backBT 클릭 이벤트
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            if (hamburgerListener != null) { // hamburger 버튼 클릭 이벤트가 있을 경우
                ivHamburger.setImageResource(R.drawable.baseline_menu_24) // hamburger 버튼이 있다.
                ivHamburger.setOnAvoidDuplicateClick { // hamburger 클릭 이벤트
                    hamburgerListener()
                }
            }
        }
    }

    /** @author 우빈
     * TextWatcher를 상속받은 textWatcher 생성
     * 필수로 재정의 해줘야 하는 멤버 함수(beforeTextChanged, onTextChanged, afterTextChanged)를 미리 정의
     * 각각의 함수 안에 각 Fragment에서 override 해서 사용할 함수들을 미리 선언
     *
     * 각 Fragment에서 사용 시 onViewCreated에서
     * val editText = binding.includedLayoutTextinput.tilEt
     * addTextWatcherToTextInputEditText(editText)
     * 와 같이 선언 후 필요한 함수를 상황에 따라 사용 가능
     */
    /**
     * 주의!!!!
     * TextInputLayout이 여러개일 경우 아래 함수 override 시 바로 상단에 어느 Layout에 대한 함수인지 필수로 추가할 것!!!!
     */
    protected open fun beforeTextChange(s: CharSequence?) {}
    protected open fun onTextChange(s: CharSequence?) {}
    protected open fun afterTextChange(s: Editable?) {}

    private val textWatcher = object : TextWatcher {
        /**
         * s: 현재 TextInputEditText에 입력된 값
         * start: s에 저장된 문자열에서 새로 추가될 문자열의 시작 위치 값
         * count: s에 새로운 문자열이 추가된 후 문자열의 길이
         * after: 새로 추가될 문자열의 길이
         */
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChange(s)
        }

        /**
         * start 위치에서 before 문자열 개수만큼 문자열이 count 개수만큼 변경되었을 때 호출
         * s: 새로 입력한 문자열이 추가된 TextInputEditText의 값을 가지고 있음
         * start: 새로 추가된 문자열의 시작 위치 값
         * before: 삭제된 기존 문자열의 개수
         * count: 새로 추가된 문자열의 개수
         */
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChange(s)
        }

        /**
         * TextInputEditText의 Text가 변경된 것을 다른 곳에 통보할 때 사용
         * s.toString()으로 현재 TextInputEditText의 Text값 불러올 수 있음
         */
        override fun afterTextChanged(s: Editable?) {
            afterTextChange(s)
        }
    }

    /** @author 우빈
     * 각 Fragment마다 아래와 같은 함수 실행해서 EditText에 TextWatcher를 추가해 줄 것
     */
    protected fun addTextWatcherToTextInputEditText(textInputEditText: TextInputEditText) {
        textInputEditText.addTextChangedListener(textWatcher)
    }

    /** @author 우빈
     * 사용법 ( withBinding(binding.includedLayoutTextinput) )
     * 1. 힌트를 설정할 때: tilLayout.hint = "힌트 입력"
     * 2. 비밀번호 설정할 떄
     *      tilLayout.isEndIconVisible = true
     *      tilLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
     * 3. 에러 메세지 표시: tilLayout.error = "에러 메세지 입력"
     * 4. 글자 수 제한할 때
     *      tilLayout.isCounterEnabled = true
     *      tilLayout.counterMaxLength = 10     // 10글자 제한. 우측 하단에 [현재 글자수]/[가능한 Max 글자수] 표시됨
     * 5. TextInputEditText 앞에 아이콘이나 이미지 추가할 때: tilLayout.startIconDrawable = resources.getDrawable(R.drawable.baseline_menu_24)
     * 6. EditText의 MaxLine 지정할 때: tilEt.maxLines = 2
     * 7. 하단에 사용자 입력 전 기본적인 안내 (ex. 정규식) 작성할 때: tilLayout.helperText="하단 메세지"
     */
}