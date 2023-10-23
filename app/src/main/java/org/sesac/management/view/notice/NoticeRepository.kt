package org.sesac.management.view.notice

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import kotlinx.coroutines.launch
import org.sesac.management.data.room.AgencyRoomDB
import org.sesac.management.data.room.EventDAO
import org.sesac.management.data.room.Notice
import org.sesac.management.data.room.NoticeDAO
import org.sesac.management.util.common.NOTICE_DB_NAME
import org.sesac.management.util.common.ioScope

class NoticeRepository(application: Application) {
    private var noticeDao: NoticeDAO
    val allNotices: LiveData<List<Notice>>?
    val homeNotices: LiveData<List<Notice>>?
    init {
        noticeDao=AgencyRoomDB.getInstance(application).generateNoticeDAO()
        allNotices = noticeDao.getAllNotice()
        homeNotices=noticeDao.getHomeNotice()
    }
    fun insertNotice(notice: Notice) {
        ioScope.launch {
            noticeDao.insertNotice(notice)
        }
    }

    fun getNotice(noticeId: Int): LiveData<Notice> = noticeDao.getNoticeById(noticeId)

    fun updateNotice(notice: Notice) {
        ioScope.launch {
            noticeDao.updateNotice(notice)
        }
    }
    fun deleteNotice(notice: Notice) {
        ioScope.launch {
            noticeDao.deleteNotice(notice)
        }
    }
    companion object {
        private var INSTANCE: NoticeRepository? = null

        fun initialize(application: Application) {
            if (INSTANCE == null) {
                INSTANCE = NoticeRepository(application)
            }
        }

        fun getInstance(): NoticeRepository {
            return INSTANCE ?: throw IllegalStateException("Please Initialize Repo")
        }
    }
}