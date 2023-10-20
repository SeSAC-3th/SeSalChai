package org.sesac.management.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manager")
data class Manager(
    // primary key
    @PrimaryKey(autoGenerate = true)
    var managerId: Int,
    val artistId: Int,
    val eventId: Int,
)
