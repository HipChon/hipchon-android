package com.gritbus.hipchon.data.model.place

import java.io.Serializable

class PlaceSearchAllData : ArrayList<PlaceSearchAllDataItem>()

data class PlaceSearchAllDataItem(
    val category: String,
    val city: String,
    val imageList: List<String>,
    val isMyplace: Boolean,
    val keyword: KeywordItem,
    val myplaceCnt: Int,
    val name: String,
    val placeId: Int,
    val postCnt: Int
) : Serializable

data class KeywordItem(
    val category: String,
    val emoji: String,
    val keyword: String,
    val keywordId: Int,
    val postCnt: Int
): Serializable
