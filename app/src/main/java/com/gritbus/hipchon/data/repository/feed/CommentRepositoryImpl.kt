package com.gritbus.hipchon.data.repository.feed

import com.gritbus.hipchon.data.datasource.feed.CommentDataSource
import com.gritbus.hipchon.data.model.feed.CommentAllData
import com.gritbus.hipchon.data.model.feed.CommentData
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val commentDataSource: CommentDataSource
): CommentRepository {

    override suspend fun getPostCommentAllData(postId: Int): Result<CommentAllData> {
        val result = commentDataSource.getPostCommentAllData(postId)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override suspend fun postComment(commentRequest: CommentData): Result<Int> {
        val result = commentDataSource.postComment(commentRequest)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override suspend fun deleteComment(commentId: Int): Result<Unit> {
        val result = commentDataSource.deleteComment(commentId)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }
}
