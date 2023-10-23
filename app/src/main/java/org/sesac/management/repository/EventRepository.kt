package org.sesac.management.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.sesac.management.data.room.AgencyRoomDB
import org.sesac.management.data.room.Artist
import org.sesac.management.data.room.ArtistDAO
import org.sesac.management.data.room.Event
import org.sesac.management.data.room.EventDAO
import org.sesac.management.data.room.Manager
import org.sesac.management.data.room.ManagerDAO

class EventRepository(context: Context) {
    val TAG: String = "로그"
    private var artistDAO: ArtistDAO
    private var eventDAO: EventDAO
    private var managerDAO: ManagerDAO
    private val coroutineIOScope = CoroutineScope(IO)
    var eventListLiveData = MutableLiveData<List<Event>>()
    var eventLiveData = MutableLiveData<Event>()

    private var eventId = MutableLiveData<Long>()

    init {
        artistDAO = AgencyRoomDB.getInstance(context).generateArtistDAO()
        eventDAO = AgencyRoomDB.getInstance(context).generateEventDAO()
        managerDAO = AgencyRoomDB.getInstance(context).generateManagerDAO()
    }

    /* C : 임시 이벤트 등록 메서드 */
    fun insertEvent(event: Event) {
        coroutineIOScope.launch(Dispatchers.IO) {
            eventDAO.insertEvent(event)
        }
    }

    /* C : 임시 매니저 등록 메서드 */
    fun insertManager(manager: Manager) {
        coroutineIOScope.launch(Dispatchers.IO) {
            managerDAO.insertManager(manager)
        }
    }

    /* C: 임시 아티스트 등록 메서드 */
    fun insertArtist(artist: Artist) {
        coroutineIOScope.launch(Dispatchers.IO) {
            artistDAO.insertArtist(artist)
        }
    }

    /**
     * R : 전체 Event를 조회하는 메서드
     * async를 통해 상태 관리 및 결과 값이 필요한 작업을 한다.
     * suspend function : 코루틴 전용 메서드, 이전 코드의 실행을 중단하고 suspend 함수 처리가
     * 완료된 후 먼춰있는 scope 코드가 실행된다.
     * @author 혜원
     */
    fun getAllEvent(): MutableLiveData<List<Event>> {
        coroutineIOScope.launch(Dispatchers.Main) {
            // Main Thread 작업
            eventListLiveData.value = asyncAllEvent()
        }
        return eventListLiveData
    }

    private suspend fun asyncAllEvent(): List<Event> {
        return coroutineIOScope.async(IO) {
            // Background 작업
            return@async eventDAO.getAllEvent()
        }.await()
    }

    /**
     * R : EventFragment에서 RecyclerView의 item을 클릭했을 때,
     * 클릭한 position의 eventId를 기준으로, EventDetailFragment에 일치하는 데이터를 불러오기 위한 메서드
     * eventId로 Event 테이블에 저장된 값을 가져온다.
     * @param eventId
     * @author 혜원
     */
    suspend fun getSearchByEventID(eventId: Int): Event {
        eventLiveData = asyncGetSearchByEventId(eventId)
        return eventLiveData.value!!
    }

    private suspend fun asyncGetSearchByEventId(eventId: Int): MutableLiveData<Event> {
        val returnedId = coroutineIOScope.async(IO) {
            return@async eventDAO.getSearchByEventID(eventId)
        }.await()
        return CoroutineScope(Dispatchers.Main).async {
            eventLiveData.value = returnedId
            eventLiveData
        }.await()
    }

    /**
     * R : EventFragment 상단의 Search layout을 통해 결과값을 가져오는 메서드
     * eventName으로 Event 테이블에 저장된 값을 가져온다.
     * @param eventId
     * @author 혜원
     */
    suspend fun getSearchEvent(eventName: String): Event {
        return asyncGetSearchEvent(eventName)
    }

    private suspend fun asyncGetSearchEvent(eventName: String): Event {
        return coroutineIOScope.async(IO) {
            return@async eventDAO.getSearchEvent(eventName)
        }.await()
    }

    /**
     * D : Event 테이블에서 EventId와 일치하는 데이터를 삭제하는 메서드
     *
     * @param eventId
     */
    suspend fun deleteEvent(eventId: Int) {
        return asyncDeleteEvent(eventId)
    }

    private suspend fun asyncDeleteEvent(eventId: Int) {
        return coroutineIOScope.async(IO) {
            return@async eventDAO.deleteEvent(eventId)
        }.await()
    }

    /**
     * D : Manager 테이블에서 EventId와 일치하는 데이터를 삭제하는 메서드
     *
     * @param eventId
     */
    suspend fun deleteEventFromManager(eventId: Int) {
        return asyncDeleteEventFromManager(eventId)
    }

    private suspend fun asyncDeleteEventFromManager(eventId: Int) {
        return coroutineIOScope.async(IO) {
            return@async eventDAO.deleteEventFromManager(eventId)
        }.await()
    }

    /**
     * D :
     *
     * @param eventId
     */
    suspend fun deleteEventWithManager(eventId: Int) {
        return asyncDeleteEventWithManager(eventId)
    }

    private suspend fun asyncDeleteEventWithManager(eventId: Int) {
        return coroutineIOScope.async(IO) {
            return@async eventDAO.deleteArtistWithEvent(eventId)
        }.await()
    }


}