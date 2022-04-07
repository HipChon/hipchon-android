package com.gritbus.hipchon.domain.model

data class PlaceData(
    val id: Int,
    val thumbnail: List<String>,
    val title: String,
    val area: String,
    val keyword: String,
    val feedCount: Int,
    val saveCount: Int,
    val isSave: Boolean,
)
