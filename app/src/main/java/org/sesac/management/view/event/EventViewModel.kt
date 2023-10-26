package org.sesac.management.view.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sesac.management.data.local.Artist
import org.sesac.management.data.local.Event
import org.sesac.management.data.local.Manager
import org.sesac.management.repository.EventRepository


class EventViewModel(private val eventRepository: EventRepository) : ViewModel() {
    var getEventDetail = MutableLiveData<Event>()
    var getArtistFromEvent = MutableLiveData<List<Artist>>()

    fun insertEvent(event: Event) {
        viewModelScope.launch {
            eventRepository.insertEvent(event)
        }
    }

    /* C : 매니저 등록 메서드 */
    fun insertManager(manager: Manager) {
        viewModelScope.launch {
            eventRepository.insertManager(manager)
        }
    }

    private val _event = MutableStateFlow<List<Event>>(emptyList())
    val event: StateFlow<List<Event>> = _event.asStateFlow()

    fun getSearch() {
        viewModelScope.launch {
            eventRepository.getAllEvent().collect {
                _event.value = it
            }
        }
    }

    fun eventByID(eventId: Int) {
        viewModelScope.launch {
            getEventDetail.value = eventRepository.getSearchByEventID(eventId)
        }
    }

    fun eventByName(eventName: String): LiveData<List<Event>> = eventRepository.getSearchEvent(eventName)

    fun updateEvent(event: Event) {
        viewModelScope.launch {
            eventRepository.updateEvent(event)
        }
    }

    fun deleteEvent(eventId: Int) {
        viewModelScope.launch {
            eventRepository.deleteEvent(eventId)
        }
    }

    fun getArtistFromEvent(eventId: Int) {
        viewModelScope.launch {
            getArtistFromEvent = eventRepository.getArtistFromEvent(eventId)
        }
    }

    //    val TAG: String = "로그"
//    private var eventDAO: EventDAO
//    private val repository = EventRepository(application)
//    var eventListLiveData = MutableLiveData<List<Event>>()
//    var eventLiveData = MutableLiveData<Event>()
//    private val coroutineIOScope = CoroutineScope(Dispatchers.IO)
//    init {
//        eventDAO = AgencyRoomDB.getInstance(application).generateEventDAO()
////        coroutineIOScope.launch {
////            insertEvent(
////                Event(
////                    name = "쇼!새싹중심", place = "상암MBC", date = Date(), description = "아이즈원",
////                    imgUri = "이미지 URI"
////                )
////            )
////            insertEvent(
////                Event(
////                    name = "새싹가요", place = "상암SBS", date = Date(), description = "르세라핌",
////                    imgUri = "이미지 URI"
////                )
////            )
////            insertEvent(
////                Event(
////                    name = "새싹뱅크", place = "KBS 별관", date = Date(), description = "뉴진스",
////                    imgUri = "이미지 URI"
////                )
////            )
////            insertManager(
////                Manager(1, 11)
////            )
////            eventDAO.getAllEvent().forEach {
////                Log.d(TAG, "getAllEvent : $it ")
////            }
////            getSearchByEventID(1)
////            getSearchEvent("새싹뱅크")
//////            deleteEvent(3)
//////            getSearchByEventID(3)
////        }
//    }
//
//    /* C : 임시 이벤트 등록 메서드 */
//    fun insertEvent(event: Event) {
//        repository.insertEvent(event)
//    }
//
//
//    /* C : 임시 아티스트 등록 메서드 */
//    fun insertArtist(artist: Artist) {
//        repository.insertArtist(artist)
//    }
//
//    /**
//     * R : 전체 Event 조회 메서드
//     */
//    fun getAllEvent() {
//        eventListLiveData = repository.getAllEvent()
//        eventListLiveData.value?.let {
//            it.forEach {
//                Log.d(TAG, "이벤트 : $it ")
//            }
//        }
//    }
//
//    /**
//     * R : RecyclerView item 클릭시
//     * 해당 position 값으로 EventDetailFragment에
//     * 데이터를 보여주기 위한 메서드
//     */
//    suspend fun getSearchByEventID(eventId: Int) {
//        CoroutineScope(Dispatchers.Main).launch {
//            eventLiveData.value = repository.getSearchByEventID(eventId)
//            Log.d(TAG, "EventID get Test : ${eventLiveData.value} ")
//        }
//    }
//
//    /**
//     * R : RecyclerView item 클릭시
//     * 해당 position 값으로 EventDetailFragment에
//     * 데이터를 보여주기 위한 메서드
//     */
//    suspend fun getSearchEvent(eventName: String) {
//        CoroutineScope(Dispatchers.Main).launch {
//            eventLiveData.value = repository.getSearchEvent(eventName)
//            Log.d(TAG, "EventName get Test : ${eventLiveData.value} ")
//        }
//    }
//
//    /**
//     * D : Event 테이블에서 EventId와 일치하는 데이터를 삭제하는 메서드
//     */
//    suspend fun deleteEvent(eventId: Int) {
//        repository.deleteEvent(eventId)
//        Log.d(TAG, "EventViewModel - deleteEvent() called")
//    }
//
//    /**
//     * D : Manager 테이블에서 EventId와 일치하는 데이터를 삭제하는 메서드
//     */
//    suspend fun deleteEventFromManager(eventId: Int) {
//        repository.deleteEventFromManager(eventId)
//        Log.d(TAG, "EventViewModel - deleteEventFromManager() called")
//    }
//
//    /**
//     * D :
//     */
//    suspend fun deleteEventWithManager(eventId: Int) {
//        repository.deleteEventWithManager(eventId)
//        Log.d(TAG, "EventViewModel - deleteEventWithManager() called")
//    }
//
//
    class EventViewModelFactory(private val eventRepository: EventRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
                EventViewModel(eventRepository) as T
            } else {
                throw IllegalArgumentException()
            }
        }
    }
}