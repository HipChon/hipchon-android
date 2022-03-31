package com.gritbus.hipchon.data.datasource.place

import com.gritbus.hipchon.data.model.place.*

interface PlaceDataSource {

    suspend fun getPlaceSearchAllData(
        userId: Int,
        cityId: Int,
        categoryId: Int,
        order: String,
    ): Result<PlaceSearchAllData>

    suspend fun getPlaceDetailData(userId: Int, placeId: Int): Result<PlaceDetailData>

    suspend fun getPlaceSearchWithHashtag(
        userId: Int,
        hashtagId: Int,
        order: String
    ): Result<PlaceSearchWithHashtagAllData>

    suspend fun getPlaceHipSearchAllData(userId: Int): Result<PlaceHipSearchAllData>

    suspend fun getLocalHipsterAllData(): Result<LocalHipsterAllData>

    suspend fun getLocalHipsterDetailData(userId: Int, hipsterId: Int): Result<LocalHipsterDetailData>
}
