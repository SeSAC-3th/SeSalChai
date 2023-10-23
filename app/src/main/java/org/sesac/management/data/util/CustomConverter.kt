package org.sesac.management.data.util

import androidx.room.TypeConverter
import java.util.Date
import java.util.UUID

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
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
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

}