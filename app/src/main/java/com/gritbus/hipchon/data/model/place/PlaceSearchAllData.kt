package com.gritbus.hipchon.data.model.place

import java.io.Serializable

class PlaceSearchAllData : ArrayList<PlaceSearchAllDataItem>()

data class PlaceSearchAllDataItem(
    val category: String,
    val city: String,
    val isMyplace: Boolean,
    val keyword: String,
    val keywordCategory: String,
    val keywordEmoji: String,
    val myplaceCnt: Int,
    val name: String,
    val placeId: Int,
    val placeImage: String,
    val postCnt: Int
) : Serializable
