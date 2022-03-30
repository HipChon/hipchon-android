package com.gritbus.hipchon.data.model.feed

import java.io.Serializable

class FeedAllData : ArrayList<FeedAllDataItem>()

data class FeedAllDataItem(
    val commentCnt: Int,
    val detail: String,
    val imageList: List<String>,
    val likeCnt: Int,
    val place: FeedPlaceItem,
    val postId: Int,
    val postTime: List<String>,
    val user: FeedUserItem
) : Serializable

data class FeedPlaceItem(
    val address: String,
    val category: String,
    val homepage: String,
    val isMyplace: Boolean,
    val name: String,
    val placeId: Int
) : Serializable

data class FeedUserItem(
    val userId: Int,
    val userImage: String,
    val userName: String,
    val userPostCnt: Int
) : Serializable
