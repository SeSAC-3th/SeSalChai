package org.sesac.management.repository

import org.sesac.management.data.local.Event
import org.sesac.management.data.local.dao.EventDAO

class EventRepository2(private val eventDAO: EventDAO) {
    companion object {
        @Volatile
        private var instance: EventRepository2? = null

        fun getInstance(eventDAO: EventDAO) =
            instance ?: synchronized(this) {
                instance ?: EventRepository2(eventDAO).also { instance = it }
            }
    }

    fun insertEvent(event: Event) = eventDAO.insertEvent(event)
    fun updateEvent(event: Event) = eventDAO.updateEvent(event)

}