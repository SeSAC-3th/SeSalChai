package org.sesac.management.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

interface ArtistDAO {
    /**
     * artist table에 있는 모든 객체를 return하는 함수
     * @return all artist
     */
    @Query("SELECT * FROM artist")
    fun getAllEvent() : List<Artist>
    @Insert
    fun insertEvent(vararg artist: Artist) : List<Long>
}