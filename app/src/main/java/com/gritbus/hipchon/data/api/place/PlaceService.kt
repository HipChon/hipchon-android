package com.gritbus.hipchon.data.api.place

import com.gritbus.hipchon.data.model.place.PlaceDetailData
import com.gritbus.hipchon.data.model.place.PlaceHipSearchAllData
import com.gritbus.hipchon.data.model.place.PlaceSearchAllData
import com.gritbus.hipchon.data.model.place.PlaceSearchWithHashtagAllData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaceService {

    @GET("place/{userId}/{cityId}/{categoryId}/{order}")
    suspend fun getPlaceSearchAllData(
        @Path("user_id") userId: Int,
        @Path("city_id") cityId: Int,
        @Path("category_id") categoryId: Int,
        @Path("order ") order: Int,
    ): Response<PlaceSearchAllData>

    @GET("place/{userId}/{placeId}")
    suspend fun getPlaceDetailData(
        @Path("user_id") userId: Int,
        @Path("place_id") placeId: Int
    ): Response<PlaceDetailData>

    @GET("place/hashtag/{userId}/{hashtagId}/{order}")
    suspend fun getPlaceSearchWithHashtag(
        @Path("user_id") userId: Int,
        @Path("hashtag_id") hashtagId: Int,
        @Path("order") order: String,
    ): Response<PlaceSearchWithHashtagAllData>

    @GET("place/hiple/{userId}")
    suspend fun getPlaceHipSearchAllData(
        @Path("user_id") userId: Int
    ): Response<PlaceHipSearchAllData>
}
