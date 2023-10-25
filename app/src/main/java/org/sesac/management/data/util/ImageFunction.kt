package org.sesac.management.data.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.sesac.management.util.common.ApplicationClass.Companion.getApplicationContext
import org.sesac.management.view.MainActivity
import java.io.IOException

object ImagePermission {
    //권한 가져오기
    val STORAGE_PERMISSION = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
    )

    //권한 플래그값 정의
    val FLAG_PERM_STORAGE = 99

    //카메라와 갤러리를 호출하는 플래그
    val FLAG_REQ_GALLERY = 101
}

fun checkPermission(
    permissions: Array<out String>,
    flag: Int,
    activityCompat: MainActivity,
): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        for (permission in permissions) {
            //만약 권한이 승인되어 있지 않다면 권한승인 요청을 사용에 화면에 호출합니다.
            if (ContextCompat.checkSelfPermission(
                    getApplicationContext(),
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(activityCompat, permissions, flag)
                return false
            }
        }
    }
    return true
}

fun convertUriToBitmap(uri: Uri, context: Context): Bitmap? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}