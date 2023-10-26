package org.sesac.management.view.notice

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.sesac.management.data.local.Notice
import org.sesac.management.repository.NoticeRepository

class NoticeViewModel(private val noticeRepository: NoticeRepository) : ViewModel() {

    private val coroutineIOScope = CoroutineScope(Dispatchers.IO)

    var selectedNoticeId: Int = 0
    var selectedNotice: LiveData<Notice>? = null
    private val noticeListLiveData = noticeRepository.allNotices
    private val homeNoticeLiveData = noticeRepository.homeNotices

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

    fun deleteNotice(noticeId: Int) {
        noticeRepository.deleteNotice(noticeId)
    }

    fun update(notice : Notice) {
        noticeRepository.updateNotice(notice)
    }

    class NoticeViewModelFactory(private val noticeRepository: NoticeRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(NoticeViewModel::class.java)) {
                NoticeViewModel(noticeRepository) as T
            } else {
                throw IllegalStateException()
            }
        }
    }
}