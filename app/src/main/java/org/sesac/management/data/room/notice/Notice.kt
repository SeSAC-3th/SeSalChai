package org.sesac.management.data.room.notice

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import org.sesac.management.data.util.CustomConverter
import java.util.Date

@Entity(tableName = "notice")
data class Notice(
    // primary key
    @PrimaryKey(autoGenerate = true)
    var noticeId: Int = 1,
    val title: String,
    val content: String,
    @TypeConverters(CustomConverter::class)
    val createdAt : Date,
)

