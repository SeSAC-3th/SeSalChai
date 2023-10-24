package org.sesac.management.repository

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.launch
import org.sesac.management.data.local.AgencyRoomDB
import org.sesac.management.data.local.Notice
import org.sesac.management.data.local.dao.NoticeDAO
import org.sesac.management.util.common.ioScope

class NoticeRepository(application: Application) {
    private var noticeDao: NoticeDAO
    val allNotices: LiveData<List<Notice>>?
    val homeNotices: LiveData<List<Notice>>?

    init {
        noticeDao = AgencyRoomDB.getInstance(application).generateNoticeDAO()
        allNotices = noticeDao.getAllNotice()
        homeNotices = noticeDao.getHomeNotice()
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