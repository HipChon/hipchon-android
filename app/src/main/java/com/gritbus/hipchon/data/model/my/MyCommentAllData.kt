package com.gritbus.hipchon.data.model.my

class MyCommentAllData : ArrayList<MyCommentAllDataItem>()

data class MyCommentAllDataItem(
    val commentId: Int,
    val detail: String,
    val post: MyCommentPost,
    val time: String
)

data class MyCommentPost(
    val image: String,
    val postId: Int
)
