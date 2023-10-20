package org.sesac.management.data.room

import androidx.room.Insert
import androidx.room.Query

interface EventDAO {

    /**
     * C: event table에 객체를 추가하는 함수
     */
    @Insert
    fun insertEvent(vararg event: Event) : List<Long>

    /**
     * R: event table에 있는 모든 객체를 return하는 함수
     * @return all notice
     */
    @Query("SELECT * FROM event")
    fun getAllEvent() : List<Event>

    /**
     * R: event table에 있는 객체중, 이름이 일치하는 event를 반환하는 함수
     * @return event
     */
    @Query("""SELECT * FROM event WHERE name=:eventName""")
    fun getSearchArtist(eventName:String) : Artist

    /**
     * D: event table에 있는 객체중, 해당하는 id의 객체를 삭제한다
     */
    @Query("""DELETE FROM event WHERE event_id=:eventId""")
    fun deletEvent(eventId:Int)

}