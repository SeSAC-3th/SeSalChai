package org.sesac.management.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import org.sesac.management.data.local.Notice

/**
 * Notice dao
 *
 * @constructor Create empty Notice dao
 * @author 종혁
 */
@Dao
interface NoticeDAO {

    /**
     * C: notice를 등록하는 함수
     */
    @Insert
    fun insertNotice(notice: Notice)

    /**
     * R: notice table에 있는 모든 객체를 return하는 함수
     * @return all notice
     */
    @Query("SELECT * FROM notice")
    fun getAllNotice(): LiveData<List<Notice>>

    @Query("""SELECT * FROM notice ORDER BY created_at DESC LIMIT 10 """)
    fun getHomeNotice(): LiveData<List<Notice>>

    /**
     * R: noticeId로 Notice 객체를 반환하는 함수
     * @return : notice
     */
    @Query("""SELECT * FROM notice WHERE notice_id=:noticeId""")
    fun getNoticeById(noticeId: Int): LiveData<Notice>

    /**
     * Update notice
     * 갱신
     * @param notice : 추가할 공지사항
     */
    @Update
    fun updateNotice(notice: Notice)

    /**
     * Delete notice
     * 공지사항 삭제
     * @param noticeId : 삭제 할 공지 사항
     */
    @Query("DELETE FROM notice WHERE notice_id= :noticeId")
    fun deleteNotice(noticeId: Int)
}