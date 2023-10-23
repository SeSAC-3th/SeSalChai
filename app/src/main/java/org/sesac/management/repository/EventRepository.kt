package org.sesac.management.repository

import org.sesac.management.data.room.Event
import org.sesac.management.data.room.EventDAO

class EventRepository(private val eventDAO: EventDAO) {
    companion object {
        @Volatile
        private var instance: EventRepository? = null

        fun getInstance(eventDAO: EventDAO) =
            instance ?: synchronized(this) {
                instance ?: EventRepository(eventDAO).also { instance = it }
            }
    }

    fun insertEvent(event: Event) = eventDAO.insertEvent(event)
    fun updateEvent(event: Event) = eventDAO.updateEvent(event)

}