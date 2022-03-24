package com.gritbus.hipchon.data.model.place

class PlaceSearchAllData : ArrayList<PlaceSearchAllDataItem>()

data class PlaceSearchAllDataItem(
    val category: String,
    val city: String,
    val isMyplace: Boolean,
    val myplaceCnt: Int,
    val name: String,
    val placeId: Int,
    val placeImage: List<String>,
    val postCnt: Int
)
