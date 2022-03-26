package com.gritbus.hipchon.data.model.place

data class PlaceHipSearchAllData(
    val data: List<PlaceHipSearchAllDataItem>
)

data class PlaceHipSearchAllDataItem(
    val category: String,
    val city: String,
    val isMyplace: Boolean,
    val myplaceCnt: Int,
    val name: String,
    val placeId: Int,
    val placeImage: List<String>,
    val postCnt: Int
)
