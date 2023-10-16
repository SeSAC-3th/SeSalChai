package org.sesac.management.data.model

import org.sesac.management.R
import java.util.Date

data class Artist(
    val name: String,
    val memberList: String,
    val festivalName: String,
    val debutDay: Date,
    val form: String,
)

data class ArtistThumbnail(
    val thumbnail: Int,
    val title: String,
    val content: String,
)
val artistList = listOf(
    ArtistThumbnail(
        thumbnail = R.drawable.bottomnavi_home,
        title = "아이유",
        content = "솔로 가수"
    ),
    ArtistThumbnail(
        thumbnail = R.drawable.baseline_add_24,
        title = "아이유",
        content = "솔로 가수"
    ),
    ArtistThumbnail(
        thumbnail = R.drawable.baseline_add_24,
        title = "아이유",
        content = "솔로 가수"
    ),
)