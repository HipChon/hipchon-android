package com.gritbus.hipchon.data.model.place

data class PlaceDetailData(
    val data: List<PlaceDetailDataItem>
)

data class PlaceDetailDataItem(
    val address: String,
    val animal: Boolean,
    val category: String,
    val city: String,
    val contact: String,
    val hashtag: List<String>,
    val hiple: Boolean,
    val holiday: String?,
    val homepage: String,
    val isMyplace: Boolean,
    val latitude: Double,
    val longitude: String,
    val markerImage: String?,
    val myplaceCnt: Int,
    val name: String,
    val oneLineIntro: String,
    val openTime: String,
    val placeId: Int,
    val placeImage: List<String>,
    val postCnt: Int
)
