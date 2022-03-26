package com.gritbus.hipchon.data.model.my

data class MyFeedAllData(
    val data: List<FeedPersonalAllDataItem>
)

data class FeedPersonalAllDataItem(
    val placeName: String,
    val postId: Int,
    val postImage: String
)
