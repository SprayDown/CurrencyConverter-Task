package org.spray.cc.model

data class Currency(
    val id: String,
    val charCode: String,
    val name: String,
    val nominal: Double,
    val value: Double
)