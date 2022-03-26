package com.gritbus.hipchon.data.api.feed

import com.gritbus.hipchon.data.model.feed.FeedAllData
import com.gritbus.hipchon.data.model.feed.FeedBestAllData
import com.gritbus.hipchon.data.model.feed.FeedWithPlaceAllData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FeedService {

    @GET("post/all/{userId}/{order}")
    suspend fun getFeedAllData(
        @Path("user_id") userId: Int,
        @Path("order") order: String
    ): Response<FeedAllData>

    @GET("post/best")
    suspend fun getFeedBestAllData(): Response<FeedBestAllData>

    @GET("post/place/{placeId}")
    suspend fun getFeedWithPlaceAllData(
        @Path("place_id") placeId: Int
    ): Response<FeedWithPlaceAllData>
}
