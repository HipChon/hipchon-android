package com.gritbus.hipchon.data.model.feed

class FeedWithPlaceAllData : ArrayList<FeedWithPlaceAllDataItem>()

data class FeedWithPlaceAllDataItem(
    val commentCnt: Int,
    val detail: String,
    val imageList: List<String>,
    val isMyplace: Boolean,
    val likeCnt: Int,
    val placeId: Int,
    val postId: Int,
    val postTime: List<Int>,
    val userId: Int,
    val userImage: String,
    val userName: String,
    val userPostCnt: Int
)
