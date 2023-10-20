package org.sesac.management.data.room.notice

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
    @Insert
    fun insertNotice(vararg notice: Notice) : List<Long>
}