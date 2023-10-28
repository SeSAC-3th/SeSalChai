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
        ArtistRepository.getInstance(
            artistDAO = database.generateArtistDAO()
        )
    }

    /**
     * Database, Repository 초기화
     * 앱이 실행되면 Database와 Repository를 한번만 초기화 해준다.
     * @author 진혁
     */

    val database by lazy { AgencyRoomDB.getInstance(this) }

    val eventRepository by lazy {
        EventRepository(
            eventDAO = database.generateEventDAO(),
            manageDAO = database.generateManagerDAO(),
            artistDAO = database.generateArtistDAO()
        )
    }
    val noticeRepository by lazy {
        NoticeRepository.getInstance(noticeDao = database.generateNoticeDAO())
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }
}