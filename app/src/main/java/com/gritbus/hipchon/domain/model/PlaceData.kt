package com.gritbus.hipchon.domain.model

data class PlaceData(
    val id: Int,
    val thumbnail: List<String>,
    val isSave: Boolean,
    val title: String,
    val distance: Int,
    val keywordFirst: String,
    val keywordSecond: String,
    val keywordThird: String,
    val saveCount: Int,
    val feedCount: Int,
    val price: String
)
