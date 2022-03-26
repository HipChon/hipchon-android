package com.gritbus.hipchon.data.model.feed

data class FeedBestAllData(
    val data: List<FeedBestAllDataItem>
)

data class FeedBestAllDataItem(
    val hashtagId: Int,
    val hashtagImage: String,
    val hashtagName: String,
    val postId: Int,
    val title: String
)
