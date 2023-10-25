package org.sesac.management.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import org.sesac.management.data.local.Artist
import org.sesac.management.data.local.ArtistType
import org.sesac.management.data.local.Event
import org.sesac.management.data.local.Manager
import org.sesac.management.data.local.Rate

@Dao
interface ArtistDAO {

    /**
     * C: artist table에 객체를 추가하는 함수
     * @return all artist
     */
    @Insert
    fun insertArtist(vararg artist: Artist): List<Long>

    /**
     * C: Rate객체를 생성하고 Artist에 rate field를 업데이트하는 함수
     * @return all artist
     */
    //Rate 단일 객체 생성
    @Insert
    fun insertRate(vararg rate: Rate): List<Long>

    //Artist 객체에 Rate 객체 id 업데이트
    @Query("UPDATE artist SET rate_id =:rateId WHERE artist_id =:artistId")
    fun linkRateToArtist(rateId: Int, artistId: Int)

    @Transaction
    suspend fun insertRateWithArtist(rate: Rate, artistId: Int) {
        // 먼저 Rate를 삽입하고 rateId를 얻습니다.
        val rateId = insertRate(rate).first() // 여기서 `insertRate`는 Rate를 데이터베이스에 삽입하는 메서드입니다.

        // 다음 Artist와 Rate를 연결합니다.
        linkRateToArtist(rateId.toInt(), artistId) // 이전에 정의한 linkRateToArtist 메서드입니다.
    }

    // TODO: artist에 포함된 event List 반환하기

    /**
     * R: artist table에 있는 모든 객체를 return하는 함수
     * @return all artist
     */
    @Query("""SELECT * FROM artist""")
    suspend fun getAllArtist(): List<Artist>

    /**
     * R: artist table에 있는 객체중, 이름이 일치하는 artist를 반환하는 함수
     * @return artist
     */
    @Query("""SELECT * FROM artist WHERE name=:artistName""")
    fun getSearchArtistByName(artistName: String): Artist

    /**
     * R: artist table에 있는 객체중, ID가 일치하는 artist를 반환하는 함수
     * @return artist
     */
    @Query("""SELECT * FROM artist WHERE artist_id=:artistId""")
    fun getSearchArtistById(artistId: Int): Artist

    /**
     * R: event table에 있는 모든 객체를 return하는 함수
     * @return all notice
     */
    @Query("SELECT * FROM artist WHERE type=:type")
    fun getArtistByType(type: ArtistType): List<Artist>

    /**
     * R:  Rate 모든객체 반환하기
     * @return List<Rate>
     */
    @Query("""SELECT * FROM rate """)
    fun getAllRate(): List<Rate>

    /**
     * R:  rateId로 Rate 객체 반환하기
     * @return rate
     */
    @Query("""SELECT * FROM rate WHERE rate_id=:rateId""")
    fun getRate(rateId: Int): Rate

    /**
     * eventId로 ManagerList 반환하는 함수
     * [getArtistFromEvent]에서 사용
     */
    @Query("""SELECT * FROM manager WHERE :artistId==manager.artist_id""")
    fun searchAllEventByArtist(artistId: Int): List<Manager>

    /**
     * R: event table에 있는 객체중, id가 일치하는 event를 반환하는 함수
     * @return event
     */
    @Query("""SELECT * FROM event WHERE event_id=:eventId""")
    fun getSearchByEventID(eventId: Int): Event

    /**
     * R: eventId로 참여중인 artist 반환하기
     */
    @Transaction
    suspend fun getEventsFromArtist(artistId: Int): MutableList<Event> {
        var managerList: List<Manager> = searchAllEventByArtist(artistId)
        var tmpEventList: MutableList<Event> = mutableListOf()
        managerList.forEach { manager ->
            var artist: Event = getSearchByEventID(manager.eventId)
            tmpEventList.add(artist)
        }
        return tmpEventList
    }

    /**
     * U: Artist 객체를 기존 속성을 복사하여, 객체르 만들고 변경하고자 하는 속성만 수정한후,
     * 해당 함수로 넘겨준다
     * @return artist
     */
    @Update
    fun updateArtist(artist: Artist)

    /**
     * D: artist table에 있는 객체중, 해당하는 id의 객체를 삭제한다
     */
    @Query("""DELETE FROM artist WHERE artist_id=:artistId""")
    fun deleteArtist(artistId: Int)

    /**
     * D: artist table에 있는 객체중, 해당하는 id의 객체를 삭제한다
     */
    @Query("""DELETE FROM manager WHERE artist_id=:artistId""")
    fun deleteArtistAtArtist(artistId: Int)

    /**
     * D: rate table에 있는 객체중, 해당하는 id의 객체를 삭제한다
     */
    @Query("""DELETE FROM rate WHERE rate_id=:rateId""")
    fun deleteRate(rateId: Int)

    /**
     * D: artist 객체를 삭제할때 관련된 객체들도 삭제하는 함수
     * [rate],[manager] 삭제
     */
    @Transaction
    suspend fun deleteArtistWithEvent(artist: Artist) {
        deleteArtist(artist.artistId)
        deleteArtistAtArtist(artist.artistId)
        if (artist.rateId != null) {
            deleteRate(artist.rateId!!)
        }
    }

}