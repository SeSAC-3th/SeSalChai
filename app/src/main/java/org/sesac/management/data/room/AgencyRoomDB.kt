package org.sesac.management.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.sesac.management.data.util.CustomConverter

/**
 * RoomDB 클래스
 * @author kimwest00
 */
@Database(
    entities = [Artist::class, Company::class, Event::class, Notice::class, Rate::class, Manager::class],
//    entities = [Notice::class],
    version = 1
)
@TypeConverters(CustomConverter::class)
abstract class AgencyRoomDB : RoomDatabase() {
    abstract fun generateNoticeDAO(): NoticeDAO
    abstract fun generateArtistDAO(): ArtistDAO
    abstract fun generateEventDAO (): EventDAO
    abstract fun generateManagerDAO (): ManagerDAO

    companion object {

        private var instance: AgencyRoomDB? = null

        fun getInstance(context: Context): AgencyRoomDB {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        fun buildDatabase(context: Context): AgencyRoomDB {
            return Room.databaseBuilder(
                context.applicationContext,
                AgencyRoomDB::class.java, "sesalchai_database.db"
            ).build()
        }
    }
}

