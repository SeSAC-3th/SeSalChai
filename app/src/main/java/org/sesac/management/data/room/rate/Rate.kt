package org.sesac.management.data.room.rate

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rate")
data class Rate(
    // primary key
    @PrimaryKey(autoGenerate = true)
    var rateId : Int,
    val income : Int,
    val popularity : Int,
    val sing : Int,
    val dance : Int,
    val performance : Int,
)
