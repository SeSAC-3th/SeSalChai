package org.sesac.management.data.model

import android.graphics.Bitmap
import org.sesac.management.data.local.Artist
import org.sesac.management.data.local.ArtistType
import java.io.Serializable
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
    val rateId: Int?,
    val imgUri: Bitmap?,
) : Serializable

data class ArtistThumbnail(
    val thumbnail: Int,
    val title: String,
    val content: String,
)

fun Artist.toModelArtist() = org.sesac.management.data.model.Artist(
    artistId = artistId,
    name = name,
    memberInfo = memberInfo,
    debutDay = debutDay,
    type = type,
    rateId = rateId,
    imgUri = imgUri
)

fun org.sesac.management.data.model.Artist.toLocalArtist() = Artist(
    artistId = artistId,
    name = name,
    memberInfo = memberInfo,
    debutDay = debutDay,
    type = type,
    rateId = rateId,
    imgUri = imgUri
)
