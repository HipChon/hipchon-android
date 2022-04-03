package com.gritbus.hipchon.data.repository.place

import com.gritbus.hipchon.data.model.place.LocalHipsterAllData
import com.gritbus.hipchon.data.model.place.LocalHipsterDetailData
import com.gritbus.hipchon.data.model.place.PlaceDetailData
import com.gritbus.hipchon.data.model.place.PlaceHipSearchAllData
import com.gritbus.hipchon.data.model.place.PlaceSaveData
import com.gritbus.hipchon.data.model.place.PlaceSearchAllData
import com.gritbus.hipchon.data.model.place.PlaceSearchWithHashtagAllData

interface PlaceRepository {

    suspend fun getPlaceSearchAllData(
        userId: Int,
        cityId: Int,
        categoryId: Int,
        order: String,
    ): Result<PlaceSearchAllData>

    suspend fun getPlaceDetailData(userId: Int,placeId: Int): Result<PlaceDetailData>

    suspend fun getPlaceSearchWithHashtag(
        userId: Int,
        hashtagId: Int,
        order: String
    ): Result<PlaceSearchWithHashtagAllData>

    suspend fun getPlaceHipSearchAllData(userId: Int): Result<PlaceHipSearchAllData>

    suspend fun getLocalHipsterAllData(): Result<LocalHipsterAllData>

    suspend fun getLocalHipsterDetailData(userId: Int, hipsterId: Int): Result<LocalHipsterDetailData>

    suspend fun savePlace(userId: Int, placeId: Int): Result<PlaceSaveData>

    suspend fun deletePlace(userId: Int, placeId: Int): Result<Unit>
}
