package org.sesac.management.view

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.sesac.management.R
import org.sesac.management.databinding.ActivityMainBinding
import org.sesac.management.util.common.ARTIST
import org.sesac.management.util.common.ApplicationClass
import org.sesac.management.util.common.CUURRENTFRAGMENTTAG
import org.sesac.management.util.common.EVENT
import org.sesac.management.util.common.HOME
import org.sesac.management.util.common.RATE
import org.sesac.management.view.artist.ArtistFragment
import org.sesac.management.view.artist.ArtistViewModel
import org.sesac.management.view.event.EventFragment
import org.sesac.management.view.home.HomeFragment
import org.sesac.management.view.rate.RateFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
     val artistViewModel: ArtistViewModel by viewModels() {
         ArtistViewModel.ArtistViewModelFactory(ApplicationClass.getApplicationContext().artistRepository)
     }


    val artistViewModel: ArtistViewModel by viewModels() {
        ArtistViewModel.ArtistViewModelFactory(ApplicationClass.getApplicationContext().artistRepository)
    }

    private lateinit var currentFragmentTag: String // 현재 보고 있는 fragment의 tag

    // 화면을 회전했을 때 지금까지 보고 있던 fragment의 tag로 해당 fragment를 찾아서 보여준다.
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentFragmentTag = savedInstanceState.getString(CUURRENTFRAGMENTTAG)
            .toString() // 화면을 회전하기 전의 currentFragment를 불러옴
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        artistViewModel.getAllArtist()

        if (savedInstanceState == null) { // 화면을 회전했을 경우 savedInstatnceState가 null이 아니다. 즉 내부 코드는 한번만 실행된다.
            supportFragmentManager
                .beginTransaction()
                .add(binding.secondFramelayout.id, HomeFragment(), HOME)
                .commitAllowingStateLoss()
            currentFragmentTag = HOME // 현재 보고 있는 fragmet의 Tag
        }
        clickBottomNavigationView()
    }

    // 화면을 회전해도 새로운 fragment를 생산하지 않고 현재 보고 있는 fragment를 불러오기 위해 tag를 저장한다.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CUURRENTFRAGMENTTAG, currentFragmentTag)
    }

    /**
     * Click bottom navigation view, 하단 바 버튼 클릭 이벤트 메서드
     *
     * 하단 바를 클릭하면, 해당 fragment의 tag로 FragmentManager의 stack을 확인해본다.
     *
     * 있으면 그 fragment를 show, 없으면 그 fragment를 add, 현재 보고 있는 화면은 hide
     * @author 진혁
     */
    private fun clickBottomNavigationView() {
        binding.secondBottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> { // 첫 번째 fragment
                    changeFragment(HOME, HomeFragment())
                }

                R.id.artistFragment -> { // 두 번째 fragment
                    changeFragment(ARTIST, ArtistFragment())
                }

                R.id.eventFragment -> { // 세 번째 fragment
                    changeFragment(EVENT, EventFragment())
                }

                R.id.rateFragment -> { // 세 번째 fragment
                    changeFragment(RATE, RateFragment())
                }
            }
            true
        }
    }

    /**
     * Change fragment, fragment 전환 메서드
     *
     * FragmentManager에 bottomNavigationView를 통해 fragment를 전환할 때 해당 fragment와 tag를 저장하고,
     *
     * 다시 그 fragment로 전환할 때 새롭게 replace하는 것이 아니라 FragmentManager에 해당 fragment의 tag가 저장되어 있는지 확인하고, 있으면 그것을 show 해준다.
     *
     * @param tag : 전환할 fragment의 tag
     * @param fragment : 전환할 fragment
     * @author 진혁
     */
    private fun changeFragment(tag: String, fragment: Fragment) {
        // supportFragmentManager에 "first"라는 Tag로 저장된 fragment 있는지 확인
        if (supportFragmentManager.findFragmentByTag(tag) == null) { // Tag가 없을 때 -> 없을 리가 없다.
            supportFragmentManager
                .beginTransaction()
                .hide(supportFragmentManager.findFragmentByTag(currentFragmentTag)!!)
                .add(binding.secondFramelayout.id, fragment, tag)
                .commitAllowingStateLoss()

        } else { // Tag가 있을 때
            // 먼저 currentFragmentTag에 저장된 '이전 fragment Tag'를 활용해 이전 fragment를 hide 시킨다.
            // supportFragmentManager에 저장된 "first"라는 Tag를 show 시킨다.
            supportFragmentManager
                .beginTransaction()
                .hide(supportFragmentManager.findFragmentByTag(currentFragmentTag)!!)
                .show(supportFragmentManager.findFragmentByTag(tag)!!)
                .commitAllowingStateLoss()
        }
        // currentFragmentTag에 '현재 fragment Tag' "first"를 저장한다.
        currentFragmentTag = tag
    }

    /**
     * 키보드 위 빈 공간을 터치하면 키보드가 사라지도록 한다
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val focusView: View? = currentFocus
        if (focusView != null) {
            val rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = ev.x.toInt()
            val y = ev.y.toInt()
            if (!rect.contains(x, y)) {
                val imm: InputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(focusView.windowToken, 0)
                focusView.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}