package org.sesac.management.data.model

data class Rate(
    // primary key
    var rateId : Int,
    val income : Int,
    val popularity : Int,
    val sing : Int,
    val dance : Int,
    val performance : Int,
)
