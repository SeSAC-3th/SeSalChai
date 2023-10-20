package org.sesac.management.data.room

import androidx.room.Insert
import androidx.room.Query

interface EventDAO {
    /**
     * event table에 있는 모든 객체를 return하는 함수
     * @return all notice
     */
    @Query("SELECT * FROM event")
    fun getAllEvent() : List<Event>
    @Insert
    fun insertEvent(vararg event: Event) : List<Long>
}