package com.gritbus.hipchon.data.api.place

import com.gritbus.hipchon.data.model.place.PlaceDetailData
import com.gritbus.hipchon.data.model.place.PlaceHipSearchAllData
import com.gritbus.hipchon.data.model.place.PlaceSearchAllData
import com.gritbus.hipchon.data.model.place.PlaceSearchWithHashtagAllData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaceService {

    @GET("place/{user_id}/{city_id}/{category_id}/{order}")
    suspend fun getPlaceSearchAllData(
        @Path("user_id") userId: Int,
        @Path("city_id") cityId: Int,
        @Path("category_id") categoryId: Int,
        @Path("order") order: String,
    ): Response<PlaceSearchAllData>

    @GET("place/{user_id}/{place_id}")
    suspend fun getPlaceDetailData(
        @Path("user_id") userId: Int,
        @Path("place_id") placeId: Int
    ): Response<PlaceDetailData>

    @GET("place/hashtag/{user_id}/{hashtag_id}/{order}")
    suspend fun getPlaceSearchWithHashtag(
        @Path("user_id") userId: Int,
        @Path("hashtag_id") hashtagId: Int,
        @Path("order") order: String,
    ): Response<PlaceSearchWithHashtagAllData>

    @GET("place/hiple/{user_id}")
    suspend fun getPlaceHipSearchAllData(
        @Path("user_id") userId: Int
    ): Response<PlaceHipSearchAllData>
}
