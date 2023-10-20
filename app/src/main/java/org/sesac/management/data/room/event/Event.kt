package org.sesac.management.data.room.event

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import org.sesac.management.data.util.CustomConverter
import java.util.Date

@Entity(tableName = "event")
data class Event(
    // primary key
    @PrimaryKey(autoGenerate = true)
    var eventId: Int = 1,
    val name: String,
    val place: String,
    @TypeConverters(CustomConverter::class)
    val date: Date,
    val description: String,
    val imgUri: String,
)

