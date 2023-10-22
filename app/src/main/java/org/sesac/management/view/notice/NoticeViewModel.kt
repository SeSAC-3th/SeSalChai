package org.sesac.management.view.notice

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.sesac.management.data.model.noticeList
import org.sesac.management.data.room.AgencyRoomDB
import org.sesac.management.data.room.ArtistDAO
import org.sesac.management.data.room.EventDAO
import org.sesac.management.data.room.ManagerDAO
import org.sesac.management.data.room.Notice
import org.sesac.management.data.room.NoticeDAO
import org.sesac.management.data.util.NOTICE_LOG
import org.sesac.management.util.common.ioScope
import java.util.Date

class NoticeViewModel(application: Application) : AndroidViewModel(application) {
    private val noticeRepository= NoticeRepository.getInstance()
    private val coroutineIOScope = CoroutineScope(Dispatchers.IO)

    val noticeListLiveData = noticeRepository.allNotices
    var selectedNotice : LiveData<Notice>?=null

    fun insertNoticeInfo(notice: Notice) {
        coroutineIOScope.launch(Dispatchers.IO) {
            noticeRepository.insertNotice(notice)
        }
    }
    fun getAllNotice() : LiveData<List<Notice>>? {
        return noticeListLiveData
    }

    fun getNotice(id: Int) : LiveData<Notice> {
        selectedNotice=noticeRepository.getNotice(id)
        return selectedNotice as LiveData<Notice>
    }

}