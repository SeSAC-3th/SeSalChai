package org.sesac.management.data.room

import androidx.room.RoomDatabase

abstact class RoomDB:RoomDatabase() {
    abstract fun artistDao(): ArtistDao
}