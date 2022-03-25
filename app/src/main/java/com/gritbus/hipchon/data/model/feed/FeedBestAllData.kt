package com.gritbus.hipchon.data.model.feed

class FeedBestAllData : ArrayList<FeedBestAllDataItem>()

data class FeedBestAllDataItem(
    val hashtagId: Int,
    val hashtagImage: String,
    val hashtagName: String,
    val postId: Int,
    val title: String
)
