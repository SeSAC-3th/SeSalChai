package org.sesac.management.data.model

import org.sesac.management.R
import java.util.Date

data class Event(
    // primary key
    var eventId: Int,
    val name: String,
    val place: String,
    val date: Date,
    val description: String,
    val imgUri: String,
)

val eventList = listOf(
    ArtistThumbnail(
        thumbnail = R.drawable.twice_chaeyeong,
        title = "행사1",
        content = "23.08.22"
    ),
    ArtistThumbnail(
        thumbnail = R.drawable.girls_generation_all,
        title = "행사2",
        content = "23.12.22"
    ),
    ArtistThumbnail(
        thumbnail = R.drawable.girls_generation_hyoyeon,
        title = "행사3",
        content = "23.11.08"
    ),
)