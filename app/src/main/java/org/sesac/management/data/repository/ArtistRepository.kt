package org.sesac.management.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.sesac.management.data.room.AgencyRoomDB
import org.sesac.management.data.room.Artist
import org.sesac.management.data.room.ArtistDAO

class ArtistRepository(context: Context) {
    private var artistDAO: ArtistDAO
    private val coroutineIOScope = CoroutineScope(IO)
    private var insert = MutableLiveData<List<Long>>()

    init {
        artistDAO = AgencyRoomDB.getInstance(context).generateArtistDAO()
        coroutineIOScope.launch {
            artistDAO.getAllArtist().forEach {
                Log.d("TAG", it.toString())
            }
        }
    }

    suspend fun insertArtist(artist: Artist): List<Long> {
        insert = asyncInsertArtist(artist)
        return insert.value as List<Long>
    }

    private suspend fun asyncInsertArtist(artist: Artist): MutableLiveData<List<Long>> {
        val insertReturn = coroutineIOScope.async(IO) {
            return@async artistDAO.insertArtist(artist)
        }.await()
        return CoroutineScope(Dispatchers.Main).async {
            insert.value = insertReturn
            insert
        }.await()
    }
}