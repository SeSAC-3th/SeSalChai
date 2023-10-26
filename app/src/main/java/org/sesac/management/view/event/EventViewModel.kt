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