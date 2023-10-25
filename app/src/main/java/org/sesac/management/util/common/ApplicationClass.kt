package org.sesac.management.util.common

import android.app.Application
import org.sesac.management.data.local.AgencyRoomDB
import org.sesac.management.repository.ArtistRepository
import org.sesac.management.repository.EventRepository
import org.sesac.management.repository.NoticeRepository

class ApplicationClass : Application() {
    companion object {
        private lateinit var appInstance: ApplicationClass
        fun getApplicationContext(): ApplicationClass = appInstance
    }
    val artistRepository by lazy {
        ArtistRepository(
            artistDAO = database.generateArtistDAO()
        )
    }

    val database by lazy { AgencyRoomDB.getInstance(this) }


    val eventRepository by lazy {
        EventRepository(
            eventDAO = database.generateEventDAO(),
            manageDAO = database.generateManagerDAO(),
            artistDAO = database.generateArtistDAO()
        )
    }
    override fun onCreate() {
        super.onCreate()
        appInstance = this
        NoticeRepository.initialize(appInstance)
    }
}