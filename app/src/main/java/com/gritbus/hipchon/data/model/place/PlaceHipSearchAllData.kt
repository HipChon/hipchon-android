package com.gritbus.hipchon.data.model.place

class PlaceHipSearchAllData : ArrayList<PlaceHipSearchAllDataItem>()

data class PlaceHipSearchAllDataItem(
    val category: String,
    val city: String,
    val isMyplace: Boolean,
    val myplaceCnt: Int,
    val name: String,
    val placeId: Int,
    val placeImage: String,
    val postCnt: Int
)
