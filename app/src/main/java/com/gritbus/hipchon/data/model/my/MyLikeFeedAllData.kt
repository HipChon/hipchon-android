package com.gritbus.hipchon.data.model.my

data class MyLikeFeedAllData(
    val data: List<MyLikeFeedAllDataItem>
)

data class MyLikeFeedAllDataItem(
    val placeName: String,
    val postId: Int,
    val postImage: String
)
