package org.sesac.management.view.artist.enroll

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.sesac.management.data.repository.ArtistRepository
import org.sesac.management.data.room.Artist

class ArtistEnrollViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ArtistRepository(application)
    var insertArtist = MutableLiveData<List<Long>>()
    suspend fun insertArtist(artist: Artist) {
        CoroutineScope(Dispatchers.Main).launch {
            insertArtist.value = repository.insertArtist(artist)
        }
    }
}