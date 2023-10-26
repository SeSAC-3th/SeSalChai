package org.sesac.management.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.sesac.management.data.local.Artist
import org.sesac.management.data.local.ArtistType
import org.sesac.management.data.local.Event
import org.sesac.management.data.local.Rate
import org.sesac.management.data.local.dao.ArtistDAO
import org.sesac.management.util.common.ioScope
import org.sesac.management.util.common.mainScope

class ArtistRepository(private val artistDAO: ArtistDAO) {
    private var getAllResult = MutableLiveData<List<Artist>>()
    private val coroutineIOScope = CoroutineScope(IO)
    private var getDetail = MutableLiveData<Artist>()
    private var getTypeResult = MutableLiveData<List<Artist>>()
    private var getEventResult = MutableLiveData<List<Event>>()
    private var getRateResult = MutableLiveData<MutableList<Rate>>()
    private var insertResult = MutableLiveData<List<Long>>()
    private var updateResult = MutableLiveData<Unit>()
    private var deleteResult = MutableLiveData<Unit>()
    companion object {
        @Volatile
        private var instance: ArtistRepository? = null
        fun getInstance(artistDAO: ArtistDAO) =
            instance ?: synchronized(this) {
                instance ?: ArtistRepository(artistDAO).also { instance = it }
            }
    }
    init {
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
        return mainScope.async {
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
        return mainScope.async {
            updateResult.value = updateReturn
            updateResult
        }.await()
    }

//    ///* getAllRate; 모든 Rate객체를 getRateResult에 저장
//    suspend fun getAllRate(): MutableLiveData<MutableList<Rate>> {
//        getRateResult = asyncGetAllRate()
//        return getRateResult
//    }
//
//    suspend fun asyncGetAllRate(): MutableLiveData<MutableList<Rate>> {
//        val getDetailValue = coroutineIOScope.async(IO) {
//            return@async artistDAO.getAllArtist()
//        }.await()
//        getDetailValue.forEach { it ->
//            it.rate?.let { it1 -> getRateResult.value?.add(it1) }
//        }
//        return CoroutineScope(Dispatchers.Main).async {
//            getRateResult
//        }.await()
//    }
//
//    // Rate용
//    fun insertRateWithArtist(rate: Rate, artistId: Int) {
//        ioScope.launch {
//            artistDAO.insertRateWithArtist(rate, artistId)
//        }
//    }
//
//    fun getAllRate() = artistDAO.getAllRate()
//    fun getRate(rateId: Int) = artistDAO.getRate(rateId)
//    fun insertRateWithArtist(rate: Rate, artistId: Int) {
//        ioScope.launch {
////            artistDAO.insertArtist(artist = )
//        }
//    }

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


    suspend fun getArtistByName(keyword:String): List<Artist>? {
        getAllResult = asyncgetArtistByName(keyword)
        return getAllResult.value
    }

    private suspend fun asyncgetArtistByName(keyword:String): MutableLiveData<List<Artist>> {
        val searchResult = coroutineIOScope.async(IO) {
            return@async artistDAO.getSearchArtistByName(keyword)
        }.await()
        return CoroutineScope(Dispatchers.Main).async {
            getAllResult.value = searchResult
            getAllResult
        }.await()
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

    suspend fun getEventFromArtist(artistId: Int): MutableLiveData<List<Event>> {
        getEventResult = asyncgetEventFromArtist(artistId)
        return getEventResult
    }

    private suspend fun asyncgetEventFromArtist(artistId: Int): MutableLiveData<List<Event>> {
        val eventReturn = coroutineIOScope.async(IO) {
            return@async artistDAO.getEventsFromArtist(artistId)
        }.await()
        return CoroutineScope(Dispatchers.Main).async {
            getEventResult.value = eventReturn
            getEventResult
        }.await()
    }
}