package com.gritbus.hipchon.data.model.place

class PlaceHipSearchAllData : ArrayList<PlaceHipSearchAllDataItem>()

data class PlaceHipSearchAllDataItem(
    val category: String,
    val city: String,
    val image: String,
    val isMyplace: Boolean,
    val keyword: KeywordItem,
    val myplaceCnt: Int,
    val name: String,
    val placeId: Int,
    val postCnt: Int
)
