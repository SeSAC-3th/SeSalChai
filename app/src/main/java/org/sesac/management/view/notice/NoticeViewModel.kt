package org.sesac.management.view.notice

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.sesac.management.data.room.Notice
import org.sesac.management.repository.NoticeRepository

class NoticeViewModel(application: Application) : AndroidViewModel(application) {
    private val noticeRepository = NoticeRepository.getInstance()
    private val coroutineIOScope = CoroutineScope(Dispatchers.IO)

    val noticeListLiveData = noticeRepository.allNotices
    var selectedNotice: LiveData<Notice>? = null
    val homeNoticeLiveData = noticeRepository.homeNotices

    fun insertNoticeInfo(notice: Notice) {
        coroutineIOScope.launch(Dispatchers.IO) {
            noticeRepository.insertNotice(notice)
        }
    }

    fun getAllNotice(): LiveData<List<Notice>>? {
        return noticeListLiveData
    }

    fun getNotice(id: Int): LiveData<Notice> {
        selectedNotice = noticeRepository.getNotice(id)
        return selectedNotice as LiveData<Notice>
    }

    fun getHomeNotice(): LiveData<List<Notice>>? {
        return homeNoticeLiveData
    }
}