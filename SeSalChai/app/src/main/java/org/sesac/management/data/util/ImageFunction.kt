package org.sesac.management.data.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.IOException

/**
 * Convert uri to bitmap
 *
 * @param uri
 * @param context
 * @return
 * @author 민서
 */
fun convertUriToBitmap(uri: Uri, context: Context): Bitmap? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}