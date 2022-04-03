package com.gritbus.hipchon.data.repository.feed

import com.gritbus.hipchon.data.model.feed.CommentAllData
import com.gritbus.hipchon.data.model.feed.CommentData

interface CommentRepository {

    suspend fun getPostCommentAllData(postId: Int): Result<CommentAllData>

    suspend fun postComment(commentRequest: CommentData): Result<Int>

    suspend fun deleteComment( commentId: Int): Result<Unit>
}
