package com.gritbus.hipchon.data.model.feed

data class FeedPersonalAllData(
    val data: List<FeedPersonalAllDataItem>
)

data class FeedPersonalAllDataItem(
    val placeName: String,
    val postId: Int,
    val postImage: String
)
