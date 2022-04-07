package com.gritbus.hipchon.data.model.place

import java.io.Serializable

data class PlaceDetailData(
    val address: String,
    val category: String,
    val contact: String,
    val holiday: String,
    val homepage: String,
    val imageList: List<String>,
    val isMyplace: Boolean,
    val keywordList: List<KeywordItem>,
    val latitude: Double,
    val longitude: Double,
    val markerImage: String,
    val myplaceCnt: Int,
    val name: String,
    val oneLineIntro: String,
    val openTime: String,
    val placeId: Int,
    val postCnt: Int
): Serializable
