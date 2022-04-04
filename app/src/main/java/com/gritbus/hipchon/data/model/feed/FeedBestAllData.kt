package com.gritbus.hipchon.data.model.feed

import java.io.Serializable

class FeedBestAllData: ArrayList<FeedBestAllDataItem>()

data class FeedBestAllDataItem(
    val hashtag: HashtagItem,
    val postId: Int,
    val title: String
): Serializable

data class HashtagItem(
    val hashtagId: Int,
    val image: String,
    val name: String
): Serializable
