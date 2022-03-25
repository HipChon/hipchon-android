package com.gritbus.hipchon.data.model.feed

class FeedPersonalAllData : ArrayList<FeedPersonalAllDataItem>()

data class FeedPersonalAllDataItem(
    val placeName: String,
    val postId: Int,
    val postImage: String
)
