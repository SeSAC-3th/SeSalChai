package org.sesac.management.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.sesac.management.repository.EventRepository2
import org.sesac.management.viewmodel.EventViewModel

class EventViewModelFactory(private val eventRepository2: EventRepository2) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            EventViewModel(eventRepository2) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}