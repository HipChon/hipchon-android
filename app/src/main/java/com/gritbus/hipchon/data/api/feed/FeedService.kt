package com.gritbus.hipchon.data.api.feed

import com.gritbus.hipchon.data.model.feed.FeedAllData
import com.gritbus.hipchon.data.model.feed.FeedAllDataItem
import com.gritbus.hipchon.data.model.feed.FeedBestAllData
import com.gritbus.hipchon.data.model.feed.FeedWithPlaceAllData
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FeedService {

    @GET("post/all/{user_id}/{order}")
    suspend fun getFeedAllData(
        @Path("user_id") userId: Int,
        @Path("order") order: String
    ): Response<FeedAllData>

    @GET("post/{user_id}/{post_id}")
    suspend fun getFeedDetailData(
        @Path("user_id") userId: Int,
        @Path("post_id") postId: Int
    ): Response<FeedAllDataItem>

    @GET("post/best")
    suspend fun getFeedBestAllData(): Response<FeedBestAllData>

    @GET("post/place/{place_id}")
    suspend fun getFeedWithPlaceAllData(
        @Path("place_id") placeId: Int
    ): Response<FeedWithPlaceAllData>

    @POST("mypost/{user_id}/{post_id}")
    suspend fun savePost(
        @Path("user_id") userId: Int,
        @Path("post_id") postId: Int
    ): Response<Int>

    @DELETE("mypost/{user_id}/{post_id}")
    suspend fun deletePost(
        @Path("user_id") userId: Int,
        @Path("post_id") postId: Int
    ): Response<Unit>
}
