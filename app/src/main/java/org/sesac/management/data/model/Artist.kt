package org.sesac.management.data.model

import android.graphics.Bitmap
import org.sesac.management.data.local.Artist
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
    val rate: Rate?,
    val imgUri: Bitmap?,
)

data class ArtistThumbnail(
    val thumbnail: Int,
    val title: String,
    val content: String,
)

/**
 * To model artist
 * Room에서 가져온 Artist를 로컬에서 필요한 데이터로 변환해주기 위한 확장함수
 *@author 우빈
 */

fun Artist.toModelArtist() = org.sesac.management.data.model.Artist(
    artistId = artistId,
    name = name,
    memberInfo = memberInfo,
    debutDay = debutDay,
    type = type,
    rate = null,
    imgUri = imgUri
)

fun org.sesac.management.data.model.Artist.toLocalArtist() = Artist(
    artistId = artistId,
    name = name,
    memberInfo = memberInfo,
    debutDay = debutDay,
    type = type,
    rate = null,
    imgUri = imgUri
)
