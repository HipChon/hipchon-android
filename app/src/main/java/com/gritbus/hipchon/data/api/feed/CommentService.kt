package com.gritbus.hipchon.data.api.feed

import com.gritbus.hipchon.data.model.feed.CommentAllData
import com.gritbus.hipchon.data.model.feed.CommentData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentService {

    @GET("postcomment/{post_id}")
    suspend fun getPostCommentAllData(
        @Path("post_id") postId: Int
    ): Response<CommentAllData>

    @POST("postcomment")
    suspend fun postComment(
        @Body commentRequest: CommentData
    ): Response<Int>

    @DELETE("postcomment/{comment_id}")
    suspend fun deleteComment(
        @Path("comment_id") commentId: Int
    ): Response<Unit>
}
