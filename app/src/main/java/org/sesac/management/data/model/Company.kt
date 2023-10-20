package org.sesac.management.data.model

data class Company(
    // primary key
    val name: String,
    val chief: String,
    val type: String,
    val description: String,
    val imgUri: String,
    val noticeId : List<Int>,
)
