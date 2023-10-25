package org.sesac.management.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.sesac.management.data.local.AgencyRoomDB
import org.sesac.management.data.local.Artist
import org.sesac.management.data.local.ArtistType
import org.sesac.management.data.local.Rate
import org.sesac.management.data.local.dao.ArtistDAO
import org.sesac.management.util.common.ARTIST
import org.sesac.management.util.common.ioScope
import org.sesac.management.util.common.mainScope

class ArtistRepository(context: Context) {
    private var artistDAO: ArtistDAO
    private val coroutineIOScope = CoroutineScope(IO)
    private var getAllResult = MutableLiveData<List<Artist>>()
    private var getDetail = MutableLiveData<Artist>()
    private var getTypeResult = MutableLiveData<List<Artist>>()
    private var insertResult = MutableLiveData<List<Long>>()
    private var updateResult = MutableLiveData<Unit>()
    private var deleteResult = MutableLiveData<Unit>()

    init {
        artistDAO = AgencyRoomDB.getInstance(context).generateArtistDAO()
        coroutineIOScope.launch {
            artistDAO.getAllArtist().forEach {
                Log.d("TAG", it.toString())
            }
        }
    }

    suspend fun getAllArtist(): List<Artist> {
        getAllResult = asyncGetAllArtist()
        return getAllResult.value as List<Artist>
    }

    private suspend fun asyncGetAllArtist(): MutableLiveData<List<Artist>> {
        val getAllReturn = ioScope.async {
            return@async artistDAO.getAllArtist()
        }.await()
        return mainScope.async {
            getAllResult.value = getAllReturn
            getAllResult
        }.await()
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

    suspend fun getArtistById(id: Int): Artist? {
        getDetail = asyncgetArtistById(id)
        return getDetail.value
    }

    private suspend fun asyncgetArtistById(id: Int): MutableLiveData<Artist> {
        val getDetailValue = coroutineIOScope.async(IO) {
            return@async artistDAO.getSearchArtistById(id)
        }.await()
        return CoroutineScope(Dispatchers.Main).async {
            getDetail.value = getDetailValue
            getDetail
        }.await()
    }

    fun getArtistByName(name: String): List<Artist> {
        return artistDAO.getSearchArtistByName(name)
    }

    suspend fun getArtistByType(type: ArtistType): List<Artist>? {
        getTypeResult = asyncgetArtistByType(type)
        return getTypeResult.value
    }

    private suspend fun asyncgetArtistByType(type: ArtistType): MutableLiveData<List<Artist>> {
        val updateReturn = coroutineIOScope.async(IO) {
            return@async artistDAO.getArtistByType(type)
        }.await()
        return CoroutineScope(Dispatchers.Main).async {
            getTypeResult.value = updateReturn
            getTypeResult
        }.await()
    }

    suspend fun deleteArtist(artist: Artist) {
        deleteResult = asyncDeleteArtist(artist)
    }

    private suspend fun asyncDeleteArtist(artist: Artist): MutableLiveData<Unit> {
        val deleteReturn = coroutineIOScope.async(IO) {
            return@async artistDAO.deleteArtistWithEvent(artist)
        }.await()
        return CoroutineScope(Dispatchers.Main).async {
            deleteResult.value = deleteReturn
            deleteResult
        }.await()
    }
}