package org.sesac.management.data.model

import org.sesac.management.R
import java.util.Date

data class Notice(
    // primary key
    var noticeId: Int,
    val title: String,
    val content: String,
    val createdAt : Date,
)

val noticeList = listOf(
    ArtistThumbnail(
        thumbnail = R.drawable.twice_chaeyeong,
        title = "공지1",
        content = "23.08.22"
    ),
    ArtistThumbnail(
        thumbnail = R.drawable.girls_generation_all,
        title = "공지2",
        content = "23.12.22"
    ),
    ArtistThumbnail(
        thumbnail = R.drawable.girls_generation_hyoyeon,
        title = "공지3",
        content = "23.11.08"
    ),
)