package org.sesac.management.data.room.artist

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import org.sesac.management.R
import org.sesac.management.data.util.CustomConverter
import java.util.Date


@Entity(tableName = "artist")
data class Artist(
    // primary key
    @PrimaryKey(autoGenerate = true)
    var artistId: Int,
    val name: String,
    val memberInfo: String,
    @TypeConverters(CustomConverter::class)
    val debutDay: Date,
    val type: ArtistType,
    val rateId: Int,
    val imgUri : String,
)

enum class ArtistType {
    SINGER,
    ACTOR,
    COMEDIAN,
    MODEL
}