package org.sesac.management.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.launch
import org.sesac.management.data.local.Notice
import org.sesac.management.data.local.dao.NoticeDAO
import org.sesac.management.util.common.ioScope

/**
 * Notice repository
 * Notice ViewModel과 연결 하는 Repository
 * @property noticeDao
 * @constructor Create empty Notice repository
 * @author 종혁
 */
class NoticeRepository(private val noticeDao: NoticeDAO) {
    // 모든 공지 사항과 home 화면에 올릴 공지 사항 보관 용도 (Repo 생성 시 초기화)
    val allNotices: LiveData<List<Notice>>?
    val homeNotices: LiveData<List<Notice>>?

    init {
        allNotices = noticeDao.getAllNotice()
        homeNotices = noticeDao.getHomeNotice()
    }

    // CREATE
    fun insertNotice(notice: Notice) {
        ioScope.launch {
            noticeDao.insertNotice(notice)
        }
    }

    // READ
    fun getNotice(noticeId: Int): LiveData<Notice> = noticeDao.getNoticeById(noticeId)

    // UPDATE
    fun updateNotice(notice: Notice) {
        ioScope.launch {
            noticeDao.updateNotice(notice)
        }
    }

    // DELETE
    fun deleteNotice(noticeId: Int) {
        ioScope.launch {
            noticeDao.deleteNotice(noticeId)
        }
    }

    // 싱글턴 패턴을 위해 Companion object 사용
    companion object {
        @Volatile
        private var INSTANCE: NoticeRepository? = null

        fun getInstance(noticeDao: NoticeDAO) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: NoticeRepository(noticeDao).also { INSTANCE = it }
        }
    }
}
