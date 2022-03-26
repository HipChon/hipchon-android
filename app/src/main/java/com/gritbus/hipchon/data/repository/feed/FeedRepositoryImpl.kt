package com.gritbus.hipchon.data.repository.feed

import com.gritbus.hipchon.data.datasource.feed.FeedDataSource
import com.gritbus.hipchon.data.model.feed.FeedAllData
import com.gritbus.hipchon.data.model.feed.FeedBestAllData
import com.gritbus.hipchon.data.model.feed.FeedWithPlaceAllData
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val feedDataSource: FeedDataSource
) : FeedRepository {

    override suspend fun getFeedAllData(userId: Int, order: String): Result<FeedAllData> {
        val result = feedDataSource.getFeedAllData(userId, order)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override suspend fun getFeedBestAllData(): Result<FeedBestAllData> {
        val result = feedDataSource.getFeedBestAllData()

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override suspend fun getFeedWithPlaceAllData(placeId: Int): Result<FeedWithPlaceAllData> {
        val result = feedDataSource.getFeedWithPlaceAllData(placeId)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }
}
