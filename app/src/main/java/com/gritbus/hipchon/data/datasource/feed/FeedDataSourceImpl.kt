package com.gritbus.hipchon.data.datasource.feed

import com.gritbus.hipchon.data.api.feed.FeedService
import com.gritbus.hipchon.data.model.feed.FeedAllData
import com.gritbus.hipchon.data.model.feed.FeedBestAllData
import com.gritbus.hipchon.data.model.feed.FeedWithPlaceAllData
import javax.inject.Inject

class FeedDataSourceImpl @Inject constructor(
    private val feedService: FeedService
) : FeedDataSource {

    override suspend fun getFeedAllData(userId: Int, order: String): Result<FeedAllData> {
        return try {
            val data = feedService.getFeedAllData(userId, order)
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

    override suspend fun getFeedBestAllData(): Result<FeedBestAllData> {
        return try {
            val data = feedService.getFeedBestAllData()
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

    override suspend fun getFeedWithPlaceAllData(placeId: Int): Result<FeedWithPlaceAllData> {
        return try {
            val data = feedService.getFeedWithPlaceAllData(placeId)
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
