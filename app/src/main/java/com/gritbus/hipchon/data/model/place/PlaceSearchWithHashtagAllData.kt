package com.gritbus.hipchon.data.model.place

data class PlaceSearchWithHashtagAllData(
    val data: List<PlaceSearchWithHashtagAllDataItem>
)

data class PlaceSearchWithHashtagAllDataItem(
    val category: String,
    val city: String,
    val isMyplace: Boolean,
    val keyword: String,
    val keywordCategory: String,
    val keywordEmoji: String,
    val myplaceCnt: Int,
    val name: String,
    val placeId: Int,
    val placeImage: List<String>,
    val postCnt: Int
)
