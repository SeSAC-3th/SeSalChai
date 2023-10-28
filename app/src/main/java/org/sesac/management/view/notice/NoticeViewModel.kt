package org.sesac.management.view.notice

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
    // 선택 된 공지 사항의 Pk 보존 용도
    var selectedNoticeId: Int = 0
    var selectedNotice: LiveData<Notice>? = null
    private val noticeListLiveData = noticeRepository.allNotices
    private val homeNoticeLiveData = noticeRepository.homeNotices

    // CRATE
    fun insertNoticeInfo(notice: Notice) {
        coroutineIOScope.launch(Dispatchers.IO) {
            noticeRepository.insertNotice(notice)
        }
    }

    // 모든 공지 사항 반환
    fun getAllNotice(): LiveData<List<Notice>>? {
        return noticeListLiveData
    }

    // READ
    fun getNotice(id: Int): LiveData<Notice> {
        selectedNotice = noticeRepository.getNotice(id)
        return selectedNotice as LiveData<Notice>
    }

    // 홈 화면에 보일 공지 사항
    fun getHomeNotice(): LiveData<List<Notice>>? {
        return homeNoticeLiveData
    }

    // UPDATE
    fun update(notice : Notice) {
        noticeRepository.updateNotice(notice)
    }

    // DELETE
    fun deleteNotice(noticeId: Int) {
        noticeRepository.deleteNotice(noticeId)
    }

    /**
     * Notice view model factory
     * ViewModel 팩토리 패턴 적용
     * @property noticeRepository
     * @constructor Create empty Notice view model factory
     */
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