package com.gritbus.hipchon.data.model.my

class MyFeedAllData: ArrayList<MyFeedAllDataItem>()

data class MyFeedAllDataItem(
    val name: String,
    val postId: Int,
    val image: String
)
