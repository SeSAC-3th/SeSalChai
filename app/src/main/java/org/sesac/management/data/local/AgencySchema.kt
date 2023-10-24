package org.sesac.management.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import org.sesac.management.data.util.CustomConverter
import java.util.Date


@Entity(
    tableName = "company", foreignKeys = [
        ForeignKey(
            entity = Notice::class,
            parentColumns = ["notice_id"],
            childColumns = ["notice_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Company(

    var chief: String,
    var type: String,
    var description: String,
    var imgUri: String,
    @ColumnInfo(name = "notice_id")
    var noticeId: List<Int>,

    // primary key
    @PrimaryKey
    val name: String,
)

@Entity(
    tableName = "notice",
    indices = [Index(value = ["notice_id"], unique = true)],
)
data class Notice(

    var title: String,
    var content: String,
    @TypeConverters(CustomConverter::class)
    @ColumnInfo(name = "created_at")
    var createdAt: Date,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "notice_id")
    val noticeId: Int = 0,
)

@Entity(
    tableName = "event", indices = [Index(value = ["event_id"], unique = true)],
)
data class Event(
    val name: String,
    var place: String,
    @TypeConverters(CustomConverter::class)
    var date: Date,
    var description: String,

    @ColumnInfo(name = "img_uri")
    var imgUri: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "event_id")
    val eventId: Int = 0,
)

@Entity(
    tableName = "artist",
    indices = [Index(value = ["artist_id"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = Rate::class,
            parentColumns = ["rate_id"],
            childColumns = ["rate_id"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class Artist(

    val name: String,
    @ColumnInfo(name = "member_info")
    var memberInfo: String,
    @TypeConverters(CustomConverter::class)
    @ColumnInfo(name = "debut_day")
    var debutDay: Date,
    var type: ArtistType,

    @ColumnInfo(name = "rate_id")
    var rateId: Int?,
    @ColumnInfo(name = "img_uri")
    var imgUri: String,

    // primary key
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "artist_id")
    val artistId: Int = 0,
)

enum class ArtistType {
    SINGER,
    ACTOR,
    COMEDIAN,
    MODEL,
    NONE
}

@Entity(
    tableName = "rate",
    indices = [Index(value = ["rate_id"], unique = true)],
)
data class Rate(

    val income: Int,
    val popularity: Int,
    val sing: Int,
    val dance: Int,
    val performance: Int,

    // primary key
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rate_id")
    var rateId: Int,
)

@Entity(
    tableName = "manager",
    foreignKeys = [
        ForeignKey(
            entity = Artist::class,
            parentColumns = ["artist_id"],
            childColumns = ["artist_id"],
            onDelete = ForeignKey.CASCADE
        ), ForeignKey(
            entity = Event::class,
            parentColumns = ["event_id"],
            childColumns = ["event_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Manager(
    @ColumnInfo(name = "artist_id")
    val artistId: Int,

    @ColumnInfo(name = "event_id")
    val eventId: Int,

    // primary key
    @PrimaryKey(autoGenerate = true)
    var managerId: Int = 0,
)
