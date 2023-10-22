package org.sesac.management.data.repository

import org.sesac.management.data.room.Artist
import org.sesac.management.data.room.ArtistDAO

class ArtistRepository(private val artistDAO: ArtistDAO) {
    suspend fun insertArtist(artist: Artist) {
        artistDAO.insertArtist(artist)
    }
}