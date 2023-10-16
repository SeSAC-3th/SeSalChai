package org.sesac.management.data.model

import org.sesac.management.R

data class Event(
    val event : String,
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