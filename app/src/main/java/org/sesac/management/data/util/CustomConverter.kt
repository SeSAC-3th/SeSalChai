package org.sesac.management.data.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.util.Date

/**
 * Custom converter
 * String, int와 같이 DB의 기본 타입이 아닌 타입을
 * 변환해주는 함수
 * @author 민서
 */
class CustomConverter {
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date? {
        return millisSinceEpoch?.let {
            Date(it)
        }
    }
    @TypeConverter
    fun fromIntList(value: List<Int>?): String? {
        // List<Int>를 문자열로 변환
        return value?.joinToString(",")
    }

    @TypeConverter
    fun toIntList(value: String?): List<Int>? {
        // 문자열을 List<Int>로 변환
        return value?.split(",")?.map { it.toInt() }
    }

    // Bitmap -> ByteArray 변환
    @TypeConverter
    fun toByteArray(bitmap : Bitmap?) : ByteArray?{
        val outputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    // ByteArray -> Bitmap 변환
    @TypeConverter
    fun toBitmap(bytes : ByteArray?) : Bitmap?{
        return bytes?.let { BitmapFactory.decodeByteArray(bytes, 0, it.size) }
    }
}