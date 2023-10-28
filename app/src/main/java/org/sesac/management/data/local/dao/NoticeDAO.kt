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
     * Insert notice
     * 등록
     * @param notice : 등록 할 공지 사항
     */
    @Insert
    fun insertNotice(notice: Notice)

    /**
     * R: 모든 공지 목록을 반환 (LiveData 사용)
     * @return all notice
     */
    @Query("SELECT * FROM notice")
    fun getAllNotice(): LiveData<List<Notice>>

    /**
     * Get home notice
     * Home 화면에 보여줄 공지를 반환 하는 메서드
     * @return 생성일 기준 최신 순으로 10개
     */
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
     * @param notice : 갱신 할 공지 사항
     */
    @Update
    fun updateNotice(notice: Notice)

    /**
     * Delete notice
     * 삭제
     * @param noticeId : 삭제 할 공지 사항의 ID 값
     */
    @Query("DELETE FROM notice WHERE notice_id= :noticeId")
    fun deleteNotice(noticeId: Int)
}