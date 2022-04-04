package com.gritbus.hipchon.data.model.feed

data class FeedCreateData(
    val detail: String,
    val keywordIdList: List<Int>,
    val placeId: Int,
    val userId: Int
)
