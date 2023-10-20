package org.sesac.management.data.room.company

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company")
data class Company(
    // primary key
    @PrimaryKey
    val name: String,
    val chief: String,
    val type: String,
    val description: String,
    val imgUri: String,
    val noticeId : List<Int>,
)
