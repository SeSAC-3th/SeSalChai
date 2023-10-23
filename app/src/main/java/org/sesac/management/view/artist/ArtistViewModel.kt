package org.sesac.management.view.artist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.sesac.management.data.room.Artist
import org.sesac.management.repository.ArtistRepository

class ArtistViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ArtistRepository(application)
    var insertArtist = MutableLiveData<List<Long>>()
    suspend fun insertArtist(artist: Artist) {
        CoroutineScope(Dispatchers.Main).launch {
            insertArtist.value = repository.insertArtist(artist)
        }
    }
}