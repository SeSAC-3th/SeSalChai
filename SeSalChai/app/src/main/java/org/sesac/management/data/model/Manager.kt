package org.sesac.management.data.model

data class Manager(
    // primary key
    var managerId: Int,
    val artistId: Int,
    val eventId: Int,
)
