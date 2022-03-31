package com.gritbus.hipchon.data.datasource.place

import com.gritbus.hipchon.data.api.place.PlaceService
import com.gritbus.hipchon.data.model.place.*
import javax.inject.Inject

class PlaceDataSourceImpl @Inject constructor(
    private val placeService: PlaceService
) : PlaceDataSource {

    override suspend fun getPlaceSearchAllData(
        userId: Int,
        cityId: Int,
        categoryId: Int,
        order: String
    ): Result<PlaceSearchAllData> {
        return try {
            val data = placeService.getPlaceSearchAllData(userId, cityId, categoryId, order)
            if (data.isSuccessful) {
                data.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Throwable(data.message()))
            } else {
                Result.failure(Throwable(data.message()))
            }
        } catch (e: Exception) {
            Result.failure(Throwable(e.message))
        }
    }

    override suspend fun getPlaceDetailData(userId: Int, placeId: Int): Result<PlaceDetailData> {
        return try {
            val data = placeService.getPlaceDetailData(userId, placeId)
            if (data.isSuccessful) {
                data.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Throwable(data.message()))
            } else {
                Result.failure(Throwable(data.message()))
            }
        } catch (e: Exception) {
            Result.failure(Throwable(e.message))
        }
    }

    override suspend fun getPlaceHipSearchAllData(userId: Int): Result<PlaceHipSearchAllData> {
        return try {
            val data = placeService.getPlaceHipSearchAllData(userId)
            if (data.isSuccessful) {
                data.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Throwable(data.message()))
            } else {
                Result.failure(Throwable(data.message()))
            }
        } catch (e: Exception) {
            Result.failure(Throwable(e.message))
        }
    }

    override suspend fun getPlaceSearchWithHashtag(
        userId: Int,
        hashtagId: Int,
        order: String
    ): Result<PlaceSearchWithHashtagAllData> {
        return try {
            val data = placeService.getPlaceSearchWithHashtag(userId, hashtagId, order)
            if (data.isSuccessful) {
                data.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Throwable(data.message()))
            } else {
                Result.failure(Throwable(data.message()))
            }
        } catch (e: Exception) {
            Result.failure(Throwable(e.message))
        }
    }

    override suspend fun getLocalHipsterAllData(): Result<LocalHipsterAllData> {
        return try {
            val data = placeService.getLocalHipsterAllData()
            if (data.isSuccessful) {
                data.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Throwable(data.message()))
            } else {
                Result.failure(Throwable(data.message()))
            }
        } catch (e: Exception) {
            Result.failure(Throwable(e.message))
        }
    }

    override suspend fun getLocalHipsterDetailData(
        userId: Int,
        hipsterId: Int
    ): Result<LocalHipsterDetailData> {
        return try {
            val data = placeService.getLocalHipsterDetailData(userId, hipsterId)
            if (data.isSuccessful) {
                data.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Throwable(data.message()))
            } else {
                Result.failure(Throwable(data.message()))
            }
        } catch (e: Exception) {
            Result.failure(Throwable(e.message))
        }
    }
}
