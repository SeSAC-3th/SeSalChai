package org.sesac.management.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.sesac.management.data.room.artist.Artist
import org.sesac.management.data.room.company.Company
import org.sesac.management.data.room.event.Event
import org.sesac.management.data.room.notice.Notice
import org.sesac.management.data.room.notice.NoticeDAO
import org.sesac.management.data.room.rate.Rate
import org.sesac.management.data.util.CustomConverter

/**
 * RoomDB 클래스
 * @author kimwest00
 */
@Database(
    entities = [Artist::class, Company::class, Event::class, Notice::class, Rate::class],
    version = 1
)
@TypeConverters(CustomConverter::class)
abstract class AgencyRoomDB : RoomDatabase() {
    abstract fun generateDAO(): ArtistDAO
    abstract fun generateNoticeDAO(): NoticeDAO

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
                AgencyRoomDB::class.java, "sesalchai_database.db")
                .build()
        }
    }
}

