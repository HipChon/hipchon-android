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

    @GET("place/user/{user_id}")
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

    @POST("myplace/{user_id}/{place_id}")
    suspend fun saveMyPlace(
        @Path("user_id") userId: Int,
        @Path("place_id") placeId: Int
    ): Response<MyPlaceSaveData>
}
