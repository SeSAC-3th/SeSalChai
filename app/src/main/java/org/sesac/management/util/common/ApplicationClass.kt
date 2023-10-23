package org.sesac.management.util.common

import android.app.Application
import org.sesac.management.data.room.AgencyRoomDB
import org.sesac.management.view.notice.NoticeRepository

class ApplicationClass : Application() {
    companion object {
        private lateinit var appInstance: ApplicationClass
        fun getApplicationContext() : ApplicationClass = appInstance
    }
    override fun onCreate() {
        super.onCreate()
        appInstance = this
        NoticeRepository.initialize(appInstance)
    }
}