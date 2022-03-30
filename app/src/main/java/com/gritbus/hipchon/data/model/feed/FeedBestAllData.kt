package com.gritbus.hipchon.data.model.feed

class FeedBestAllData: ArrayList<FeedBestAllDataItem>()

data class FeedBestAllDataItem(
    val hashtag: HashtagItem,
    val postId: Int,
    val title: String
)

data class HashtagItem(
    val hashtagId: Int,
    val image: String,
    val name: String
)
