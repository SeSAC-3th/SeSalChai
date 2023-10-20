package org.sesac.management.view.notice

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.sesac.management.data.room.AgencyRoomDB
import org.sesac.management.data.room.Notice
import org.sesac.management.data.room.NoticeDAO
import org.sesac.management.data.util.NOTICE_LOG
import java.util.Date

class NoticeViewModel(application: Application) : AndroidViewModel(application) {
    private var noticeDAO: NoticeDAO
    private val coroutineIOScope = CoroutineScope(Dispatchers.IO)

    init {
        noticeDAO = AgencyRoomDB.getInstance(application).generateNoticeDAO()
        coroutineIOScope.launch {
            insertMemberInfo(Notice(title = "제목 1", content = "내용 1", createdAt = Date()))
            insertMemberInfo(Notice(title = "제목 2", content = "내용 2", createdAt = Date()))
            noticeDAO.getAllNotice().forEach {
                Log.d(NOTICE_LOG, it.toString())
            }
            Log.d(NOTICE_LOG,noticeDAO.getNoticeById(1).toString())
        }
    }

    fun insertMemberInfo(notice: Notice) {
        coroutineIOScope.launch(Dispatchers.IO) {
            noticeDAO.insertNotice(notice)
        }
    }
}