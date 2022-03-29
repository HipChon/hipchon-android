package com.gritbus.hipchon.data.model.my

class MyFeedAllData: ArrayList<MyFeedAllDataItem>()

data class MyFeedAllDataItem(
    val placeName: String,
    val postId: Int,
    val postImage: String
)
