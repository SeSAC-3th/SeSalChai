package org.sesac.management.data.model

import java.util.Date

data class Notice(
    // primary key
    var noticeId: Int,
    val title: String,
    val content: String,
    val createdAt: Date,
)