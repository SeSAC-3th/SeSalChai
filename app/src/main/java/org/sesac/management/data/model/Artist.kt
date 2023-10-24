package org.sesac.management.data.model

import org.sesac.management.R
import org.sesac.management.data.local.ArtistType
import java.util.Date

enum class ArtistType {
    SINGER,
    ACTOR,
    COMEDIAN,
    MODEL,
    NULL
}
data class Artist(
    // primary key
    var artistId: Int,
    val name: String,
    val memberInfo: String,
    val debutDay: Date,
    val type: ArtistType,
    val rateId: Int,
    val imgUri: String,
)

data class ArtistThumbnail(
    val thumbnail: Int,
    val title: String,
    val content: String,
)

val artistList = listOf(
    ArtistThumbnail(
        thumbnail = R.drawable.twice_chaeyeong,
        title = "아이유",
        content = "솔로 가수"
    ),
    ArtistThumbnail(
        thumbnail = R.drawable.girls_generation_hyoyeon,
        title = "아이유",
        content = "솔로 가수"
    ),
    ArtistThumbnail(
        thumbnail = R.drawable.girls_generation_all,
        title = "아이유",
        content = "솔로 가수"
    ),
)
