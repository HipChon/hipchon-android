package com.gritbus.hipchon.data.model.feed

class CommentAllData: ArrayList<CommentAllDataItem>()

data class CommentAllDataItem(
    val commentId: Int,
    val detail: String,
    val time: String,
    val user: CommentUser
)

data class CommentUser(
    val image: String,
    val name: String,
    val userId: Int
)
