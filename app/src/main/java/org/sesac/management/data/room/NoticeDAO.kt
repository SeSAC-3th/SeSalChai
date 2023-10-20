package org.sesac.management.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoticeDAO {
    /**
     * notice table에 있는 모든 객체를 return하는 함수
     * @return all notice
     */
    @Query("SELECT * FROM notice")
    fun getAllNotice() : List<Notice>

    /**
     * noticeId로 Notice 객체를 반환하는 함수
     */
    @Query("""SELECT * FROM notice WHERE notice_id=:noticeId""")
    fun getNoticeById(noticeId:Int) : Notice

    /**
     * notice를 등록하는 함수
     */
    @Insert
    fun insertNotice(vararg notice: Notice) : List<Long>
}