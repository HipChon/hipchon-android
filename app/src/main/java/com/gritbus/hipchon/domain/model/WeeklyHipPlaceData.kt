package com.gritbus.hipchon.domain.model

data class WeeklyHipPlaceData(
    val id: Int,
    val title: String,
    val area: Area,
    val keyword: String,
    val saveCount: Int,
    val feedCount: Int,
    val isSave: Boolean,
    val imageUrl: String
)
