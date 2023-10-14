package org.sesac.management.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import org.sesac.management.R
import org.sesac.management.databinding.ActivityMainBinding
import org.sesac.management.view.artist.ArtistFragment
import org.sesac.management.view.event.EventFragment
import org.sesac.management.view.home.HomeFragment
import org.sesac.management.view.rate.RateFragment

const val HOME = "home"
const val ARTIST = "artist"
const val EVENT = "event"
const val RATE = "rate"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var currentFragmentTag: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        supportFragmentManager
            .beginTransaction()
            .add(binding.secondFramelayout.id, HomeFragment(), HOME)
            .commitAllowingStateLoss()
        currentFragmentTag = HOME // 현재 보고 있는 fragmet의 Tag

        // 네비게이션 버튼 클릭시 프래그먼트 전환
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

}