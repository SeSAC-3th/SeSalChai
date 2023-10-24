package org.sesac.management.util.common

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.sesac.management.util.common.ApplicationClass.Companion.getApplicationContext

// BaseFragment에서 사용하는 typealias
typealias FragmentInflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

// fragment들의 Tag
const val HOME = "home"
const val ARTIST = "artist"
const val EVENT = "event"
const val RATE = "rate"

// 화면 회전시 번들에 저장하고 불러올 때 사용하는 KEY
const val CUURRENTFRAGMENTTAG = "currentfragment"

// Rx Event 에러 태그
const val RXERROR = "RX_ERROR"

// Rx Event 더블 클릭 간격 시간
const val CLICK_INTERVAL_TIME = 300L

// Rx Event 텍스트 완성 시간
const val INPUT_COMPLETE_TIME = 1000L

val defaultScope = CoroutineScope(Dispatchers.Default)
val ioScope = CoroutineScope(Dispatchers.IO)
val mainScope = CoroutineScope(Dispatchers.Main)

//DB Table Name
const val NOTICE_DB_NAME = "tb_notice"

//
fun showToastMessage(message: String) {
    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show()
}