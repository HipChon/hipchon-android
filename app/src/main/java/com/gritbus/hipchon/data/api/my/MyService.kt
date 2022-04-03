package com.gritbus.hipchon.data.api.my

import com.gritbus.hipchon.data.model.my.MyFeedAllData
import com.gritbus.hipchon.data.model.my.MyLikeFeedAllData
import com.gritbus.hipchon.data.model.my.MyPlaceAllData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MyService {

    @GET("post/user/{user_id}")
    suspend fun getMyFeedAllData(
        @Path("user_id") userId: Int
    ): Response<MyFeedAllData>

    @GET("mypost/{user_id}")
    suspend fun getMyLikeFeedAllData(
        @Path("user_id") userId: Int
    ): Response<MyLikeFeedAllData>

    @GET("myplace/{user_id}")
    suspend fun getMyPlace(
        @Path("user_id") userId: Int
    ): Response<MyPlaceAllData>

    @GET("myplace/{user_id}/{category_id}")
    suspend fun getMyPlaceWithCategory(
        @Path("user_id") userId: Int,
        @Path("category_id") category: Int
    ): Response<MyPlaceAllData>
}
