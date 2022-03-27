package com.gritbus.hipchon.data.model.my

data class MyFeedAllData(
    val data: List<MyFeedAllDataItem>
)

data class MyFeedAllDataItem(
    val placeName: String,
    val postId: Int,
    val postImage: String
)
