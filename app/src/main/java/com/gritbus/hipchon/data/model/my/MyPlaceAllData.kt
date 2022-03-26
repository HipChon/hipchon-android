package com.gritbus.hipchon.data.model.my

data class MyPlaceAllData(
    val data: List<MyPlaceAllDataItem>
)

data class MyPlaceAllDataItem(
    val address: String,
    val category: String,
    val image: Any,
    val memo: Any,
    val myplaceCnt: Int,
    val name: String,
    val placeId: Int,
    val postCnt: Int
)
