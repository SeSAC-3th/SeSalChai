package org.sesac.management.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.sesac.management.data.local.Artist
import org.sesac.management.data.local.Event
import org.sesac.management.data.local.Manager

/**
 * Event dao
 *
 * @constructor Create empty Event dao
 * @author 혜원
 */
@Dao
interface EventDAO {

    /**
     * C: event table에 객체를 추가하는 함수
     */
    @Insert
    suspend fun insertEvent(vararg event: Event)

    /**
     * R: event table에 있는 모든 객체를 return하는 함수
     * @return all notice
     */
    @Query("SELECT * FROM event")
    fun getAllEvent(): Flow<List<Event>>

    /**
     * R: event table에 있는 객체중, id가 일치하는 event를 반환하는 함수
     * @return event
     */
    @Query("""SELECT * FROM artist WHERE artist_id=:artistId""")
    suspend fun getSearchByArtistID(artistId: Int): Artist

    /**
     * R: eventId로 참여중인 artist 반환하기
     */
    @Query("""SELECT * FROM manager WHERE :eventId==manager.event_id""")
    fun searchAllArtistByEvent(eventId: Int): List<Manager>

    @Transaction
    suspend fun getEventsFromArtist(eventId: Int): MutableList<Artist> {
        val managerList: List<Manager> =
            searchAllArtistByEvent(eventId) // eventId에 해당하는 manager 객체를 모두 조회
        val tmpEventList: MutableList<Artist> =
            mutableListOf() // artistId 값만 저장하기 위해 tmpEventList를 선언
        managerList.forEach { manager -> // 가져온 List<Manager>의 사이즈만큼 반복문을 실행
            var events: Artist = getSearchByArtistID(manager.artistId) // artistId 값을 조회하고
            tmpEventList.add(events) // events에 가져온 artistId 값만 추가한다.
        }
        return tmpEventList
    }


    /**
     * R: event table에 있는 객체중, id가 일치하는 event를 반환하는 함수
     * @return event
     */
    @Query("""SELECT * FROM event WHERE event_id=:eventId""")
    fun getSearchByEventID(eventId: Int): Flow<Event>

    /**
     * R: event table에 있는 객체중, 이름이 일치하는 event를 반환하는 함수
     * @return event
     */
    @Query("""SELECT * FROM event WHERE name=:eventName""")
    fun getSearchEvent(eventName: String): LiveData<List<Event>>

    /**
     * R: 가장 최근에 만들어진 event를 불러오는 함수
     * @return
     */
    @Query("SELECT * FROM event ORDER BY event_id DESC LIMIT 1")
    fun getMostRecentEvent(): Event?

    /**
     * U: Event 객체를 기존 속성을 복사하여, 객체를 만들고 변경하고자 하는 속성만 수정한후,
     * 해당 함수로 넘겨준다
     * @return event
     */
    @Update
    suspend fun updateEvent(event: Event)

    /**
     * D: event table에 있는 객체중, 해당하는 id의 객체를 삭제한다
     */
    @Query("""DELETE FROM event WHERE event_id=:eventId""")
    suspend fun deleteEvent(eventId: Int)

    /**
     * D: artist table에 있는 객체중, 해당하는 id의 객체를 삭제한다
     */
    @Query("""DELETE FROM manager WHERE event_id=:eventId""")
    suspend fun deleteEventFromManager(eventId: Int)

    /**
     * D: artist 객체를 삭제할때 관련된 객체들도 삭제하는 함수
     * [manager] 삭제
     */
    @Transaction
    suspend fun deleteArtistWithEvent(eventId: Int) {
        deleteEvent(eventId)
        deleteEventFromManager(eventId)
    }
}