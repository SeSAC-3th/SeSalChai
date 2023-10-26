package org.sesac.management.repository

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.launch
import org.sesac.management.data.local.AgencyRoomDB
import org.sesac.management.data.local.Notice
import org.sesac.management.data.local.dao.NoticeDAO
import org.sesac.management.util.common.ioScope

class NoticeRepository(private val noticeDao: NoticeDAO) {
    val allNotices: LiveData<List<Notice>>?
    val homeNotices: LiveData<List<Notice>>?

    init {
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

    fun deleteNotice(noticeId: Int) {
        ioScope.launch {
            noticeDao.deleteNotice(noticeId)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: NoticeRepository? = null

        fun getInstance(noticeDao: NoticeDAO) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: NoticeRepository(noticeDao).also { INSTANCE = it }
        }
    }
}
