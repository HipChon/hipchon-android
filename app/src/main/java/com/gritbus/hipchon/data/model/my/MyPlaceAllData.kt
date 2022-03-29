package com.gritbus.hipchon.data.model.my

class MyPlaceAllData: ArrayList<MyPlaceAllDataItem>()

data class MyPlaceAllDataItem(
    val address: String,
    val category: String,
    val image: String,
    val memo: String,
    val myplaceCnt: Int,
    val name: String,
    val placeId: Int,
    val postCnt: Int
)
