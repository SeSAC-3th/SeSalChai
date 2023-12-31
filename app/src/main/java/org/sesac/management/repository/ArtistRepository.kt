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

/**
 * Artist repository
 *
 * @property artistDAO
 * @constructor Create empty Artist repository
 * @author 민서, 우빈
 */
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

    //Singleton으로 객체 생성
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

    /**
     * 아티스트 전체 조회를 하기 위한 함수
     * @author 우빈
     */
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

    /**
     * 아티스트를 등록하기 위한 함수
     * @param Artist 객체
     * @author 우빈
     */
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

    /**
     * 아티스트에 대한 정보를 수정했을 때 Room에 해당 아티스트의 정보를 업데이트 하기 위한 함수
     * @author 우빈
     */
    fun updateArtist(artist: Artist) {
        ioScope.launch {
            artistDAO.updateArtist(artist)
        }
    }

    ///* getAllRate; 모든 Rate객체를 getRateResult에 저장
    suspend fun getAllRate(): MutableLiveData<MutableList<Rate>> {
        getRateResult = asyncGetAllRate()
        return getRateResult
    }

    suspend fun asyncGetAllRate(): MutableLiveData<MutableList<Rate>> {
        val getDetailValue = coroutineIOScope.async(IO) {
            return@async artistDAO.getAllArtist()
        }.await()
        getDetailValue.forEach { it ->
            it.rate?.let { it1 -> getRateResult.value?.add(it1) }
        }
        return CoroutineScope(Dispatchers.Main).async {
            getRateResult
        }.await()
    }

    // Rate용
    /**
     * 아티스트 등록 후 아티스트 상세 페이지에서 아티스트에 대한 평점을 등록하기 위한 함수
     * @author 우빈
     */
    fun insertRateWithArtist(rate: Rate, artistId: Int) {
        ioScope.launch {
            artistDAO.insertRateWithArtist(
                performance = rate.performance,
                income = rate.income,
                dance = rate.dance,
                popularity = rate.popularity,
                sing = rate.sing,
                average = rate.average,
                artistId = artistId,
            )
        }
    }

    /**
     * R: artist table에 있는 객체중, ID가 일치하는 artist를 반환하는 함수
     * artist 상세페이지 접근시 사용
     * @return artist
     */
    //asycn 함수에서 받아온 결과값을 repository 내부 변수에 업데이트
    suspend fun getArtistById(id: Int): Artist? {
        getDetail = asyncgetArtistById(id)
        return getDetail.value
    }

    //async 함수를 통해 DAO로 부터 검색결과를 얻어내고
    //Main scope에서 getDetail에 해당 결과를 업데이트 해준다
    private suspend fun asyncgetArtistById(id: Int): MutableLiveData<Artist> {
        val getDetailValue = coroutineIOScope.async(IO) {
            return@async artistDAO.getSearchArtistById(id)
        }.await()
        return CoroutineScope(Dispatchers.Main).async {
            getDetail.value = getDetailValue
            getDetail
        }.await()
    }

    /**
     * R: artist 검색시 사용
     * 모두 일치해야 검색가능
     * @return artist
     */
    suspend fun getArtistByName(keyword: String): List<Artist>? {
        getAllResult = asyncgetArtistByName(keyword)
        return getAllResult.value
    }

    private suspend fun asyncgetArtistByName(keyword: String): MutableLiveData<List<Artist>> {
        val searchResult = coroutineIOScope.async(IO) {
            return@async artistDAO.getSearchArtistByName(keyword)
        }.await()
        return CoroutineScope(Dispatchers.Main).async {
            getAllResult.value = searchResult
            getAllResult
        }.await()
    }

    /**
     * R: artist type을 비교하여 반환
     * @return artist
     */
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

    /**
     * D: artist 객체 삭제
     * @return artist
     */
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

    /**
     * R: artist가 참여하는 Event List 반환
     * @return List<Event>
     */
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