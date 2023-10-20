package org.sesac.management.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

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
    @Insert
    fun insertRate(vararg rate: Rate): List<Long>

    @Query("UPDATE artist SET rate_id =:rateId WHERE artist_id =:artistId")
    fun linkRateToArtist(rateId: Int, artistId: Int)

    @Transaction
    suspend fun insertRateWithArtist(rate: Rate, artistId: Int) {
        // 먼저 Rate를 삽입하고 rateId를 얻습니다.
        val rateId = insertRate(rate).first() // 여기서 `insertRate`는 Rate를 데이터베이스에 삽입하는 메서드입니다.

        // 다음 Artist와 Rate를 연결합니다.
        linkRateToArtist(rateId.toInt(), artistId) // 이전에 정의한 linkRateToArtist 메서드입니다.
    }

    /**
     * R: artist table에 있는 모든 객체를 return하는 함수
     * @return all artist
     */
    @Query("""SELECT * FROM artist""")
    fun getAllArtist(): List<Artist>

    /**
     * R: artist table에 있는 객체중, 이름이 일치하는 artist를 반환하는 함수
     * @return artist
     */
    @Query("""SELECT * FROM artist WHERE name=:artistName""")
    fun getSearchArtist(artistName: String): Artist

    /**
     * D: artist table에 있는 객체중, 해당하는 id의 객체를 삭제한다
     */
    @Query("""DELETE FROM artist WHERE artist_id=:artistId""")
    fun deletArtist(artistId: Int)

}