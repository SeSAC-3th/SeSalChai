package org.sesac.management.view.artist.enroll

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.sesac.management.data.repository.ArtistRepository
import org.sesac.management.data.room.Artist

class ArtistEnrollViewModel(private val repository: ArtistRepository) : ViewModel() {
    fun insertArtist(artist: Artist) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertArtist(artist)
        }
    }
}