package org.sesac.management.repository

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
import org.sesac.management.data.room.Rate

class ArtistRepository(context: Context) {
    private var artistDAO: ArtistDAO
    private val coroutineIOScope = CoroutineScope(IO)
    private var insertResult = MutableLiveData<List<Long>>()
    private var updateResult = MutableLiveData<Unit>()

    init {
        artistDAO = AgencyRoomDB.getInstance(context).generateArtistDAO()
        coroutineIOScope.launch {
            artistDAO.getAllArtist().forEach {
                Log.d("TAG", it.toString())
            }
        }
    }

    suspend fun insertArtist(artist: Artist): List<Long> {
        insertResult = asyncInsertArtist(artist)
        return insertResult.value as List<Long>
    }

    private suspend fun asyncInsertArtist(artist: Artist): MutableLiveData<List<Long>> {
        val insertReturn = coroutineIOScope.async(IO) {
            return@async artistDAO.insertArtist(artist)
        }.await()
        return CoroutineScope(Dispatchers.Main).async {
            insertResult.value = insertReturn
            insertResult
        }.await()
    }

    suspend fun updateArtist(artist: Artist): Unit? {
        updateResult = asyncUpdateArtist(artist)
        return updateResult.value
    }

    private suspend fun asyncUpdateArtist(artist: Artist): MutableLiveData<Unit> {
        val updateReturn = coroutineIOScope.async(IO) {
            return@async artistDAO.updateArtist(artist)
        }.await()
        return CoroutineScope(Dispatchers.Main).async {
            updateResult.value = updateReturn
            updateResult
        }.await()
    }


    // Rate용
    fun insertRate(rate: Rate) = artistDAO.insertRate(rate)
    fun updateRate(rateId: Int, artistId: Int) = artistDAO.linkRateToArtist(rateId, artistId)
    fun getAllRate() = artistDAO.getAllRate()
    fun getRate(rateId: Int) = artistDAO.getRate(rateId)
}