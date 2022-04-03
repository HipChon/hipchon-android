package com.gritbus.hipchon.data.datasource.feed

import com.gritbus.hipchon.data.api.feed.CommentService
import com.gritbus.hipchon.data.model.feed.CommentAllData
import com.gritbus.hipchon.data.model.feed.CommentData
import javax.inject.Inject

class CommentDatasourceImpl @Inject constructor(
    private val commentService: CommentService
): CommentDataSource {

    override suspend fun getPostCommentAllData(postId: Int): Result<CommentAllData> {
        return try {
            val data = commentService.getPostCommentAllData(postId)
            if (data.isSuccessful) {
                data.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Throwable(data.message()))
            } else {
                Result.failure(Throwable(data.message()))
            }
        } catch (e: Exception) {
            Result.failure(Throwable(e.message))
        }
    }

    override suspend fun postComment(commentRequest: CommentData): Result<Int> {
        return try {
            val data = commentService.postComment(commentRequest)
            if (data.isSuccessful) {
                data.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Throwable(data.message()))
            } else {
                Result.failure(Throwable(data.message()))
            }
        } catch (e: Exception) {
            Result.failure(Throwable(e.message))
        }
    }

    override suspend fun deleteComment(commentId: Int): Result<Unit> {
        return try {
            val data = commentService.deleteComment(commentId)
            if (data.isSuccessful) {
                data.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Throwable(data.message()))
            } else {
                Result.failure(Throwable(data.message()))
            }
        } catch (e: Exception) {
            Result.failure(Throwable(e.message))
        }
    }
}
