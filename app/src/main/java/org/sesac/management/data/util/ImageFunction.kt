package org.sesac.management.data.util

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import org.sesac.management.data.util.ImagePermission.FLAG_PERM_STORAGE
import org.sesac.management.util.common.ARTIST
import org.sesac.management.util.common.ApplicationClass.Companion.getApplicationContext
import org.sesac.management.view.MainActivity

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
fun checkPermission(permissions: Array<out String>, flag: Int,activityCompat: MainActivity): Boolean {
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
fun converUriToBitmap(uri: Uri,contentResolver:ContentResolver): Bitmap?{
    var bitmap:Bitmap? = null
    if (Build.VERSION.SDK_INT < 28) {
        bitmap = MediaStore.Images.Media
            .getBitmap(contentResolver, uri)
    } else {
        val decode = contentResolver?.let { it1 ->
            ImageDecoder.createSource(
                it1,
                uri
            )
        }
        bitmap = decode?.let { it1 -> ImageDecoder.decodeBitmap(it1) }
    }
    Log.d(ARTIST, "onViewCreated: $bitmap")
    return bitmap
}
