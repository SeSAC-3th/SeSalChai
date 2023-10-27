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
import org.sesac.management.util.common.defaultDispatcher
import java.util.Date

/**
 * Event view model
 *
 * @property eventRepository
 * @constructor Create empty Event view model
 * @author 혜원, 진혁
 */
class EventViewModel(private val eventRepository: EventRepository) : ViewModel() {
    var getArtistFromEvent = MutableLiveData<List<Artist>>()

    /* C : 이벤트 등록 메서드 */
    fun insertEvent(event: Event) {
        viewModelScope.launch() {
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
    
    /* R : 이벤트 전체 조회 메서드 */
    fun getSearch() {
        viewModelScope.launch {
            eventRepository.getAllEvent().collect {
                _event.value = it
            }
        }
    }
    
    private val _eventDetail = MutableStateFlow<Event>(Event("", "", Date(), ""))
    val eventDetail: StateFlow<Event> = _eventDetail.asStateFlow()
    
    /* R : 해당하는 이벤트 ID로 조회하는 메서드 */
    fun eventByID(eventId: Int) {
        viewModelScope.launch {
            eventRepository.getSearchByEventID(eventId).collect {
                _eventDetail.value = it
            }
        }
    }
    
    /* R : 해당하는 이벤트 Name으로 조회하는 메서드 */
    fun eventByName(eventName: String): LiveData<List<Event>> =
        eventRepository.getSearchEvent(eventName)

    /* R : Manager에서 해당하는 이벤트 Id 값과 일치하는 데이터의 아티스트 Id 값을 조회하는 메서드 */
    fun getArtistFromEvent(eventId: Int) {
        viewModelScope.launch(defaultDispatcher) {
            getArtistFromEvent.postValue(eventRepository.getArtistFromEvent(eventId))
        }
    }

    /* U : 이벤트 수정 메서드 */
    fun updateEvent(event: Event) {
        viewModelScope.launch {
            eventRepository.updateEvent(event)
        }
    }
    
    /* D : 이벤트 삭제 메서드 */
    fun deleteEvent(eventId: Int) {
        viewModelScope.launch {
            eventRepository.deleteEvent(eventId)
        }
    }

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