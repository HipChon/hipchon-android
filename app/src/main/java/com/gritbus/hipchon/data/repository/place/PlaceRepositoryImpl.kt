package com.gritbus.hipchon.data.repository.place

import com.gritbus.hipchon.data.datasource.place.PlaceDataSource
import com.gritbus.hipchon.data.model.place.*
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val placeDataSource: PlaceDataSource
) : PlaceRepository {

    override suspend fun getPlaceSearchAllData(
        userId: Int,
        cityId: Int,
        categoryId: Int,
        order: String
    ): Result<PlaceSearchAllData> {
        val result = placeDataSource.getPlaceSearchAllData(userId, cityId, categoryId, order)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override suspend fun getPlaceDetailData(userId: Int, placeId: Int): Result<PlaceDetailData> {
        val result = placeDataSource.getPlaceDetailData(userId, placeId)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override suspend fun getPlaceSearchWithHashtag(
        userId: Int,
        hashtagId: Int,
        order: String
    ): Result<PlaceSearchWithHashtagAllData> {
        val result = placeDataSource.getPlaceSearchWithHashtag(userId, hashtagId, order)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override suspend fun getPlaceHipSearchAllData(userId: Int): Result<PlaceHipSearchAllData> {
        val result = placeDataSource.getPlaceHipSearchAllData(userId)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override suspend fun getLocalHipsterAllData(): Result<LocalHipsterAllData> {
        val result = placeDataSource.getLocalHipsterAllData()

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override suspend fun getLocalHipsterDetailData(
        userId: Int,
        hipsterId: Int
    ): Result<LocalHipsterDetailData> {
        val result = placeDataSource.getLocalHipsterDetailData(userId, hipsterId)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }
}
