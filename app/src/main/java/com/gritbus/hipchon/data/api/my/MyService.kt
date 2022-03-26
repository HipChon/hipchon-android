package com.gritbus.hipchon.data.api.my

import com.gritbus.hipchon.data.model.my.MyFeedAllData
import com.gritbus.hipchon.data.model.my.MyLikeFeedAllData
import com.gritbus.hipchon.data.model.my.MyPlaceAllData
import com.gritbus.hipchon.data.model.my.MyPlaceSaveData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MyService {

    @GET("place/user/{userId}")
    suspend fun getMyFeedAllData(
        @Path("user_id") userId: Int
    ): Response<MyFeedAllData>

    @GET("mypost/{userId}")
    suspend fun getMyLikeFeedAllData(
        @Path("user_id") userId: Int
    ): Response<MyLikeFeedAllData>

    @GET("myplace/{userId}")
    suspend fun getMyPlace(
        @Path("user_id") userId: Int
    ): Response<MyPlaceAllData>

    @POST("myplace/{userId}/{placeId}")
    suspend fun saveMyPlace(
        @Path("user_id") userId: Int,
        @Path("place_id") placeId: Int
    ): Response<MyPlaceSaveData>
}
