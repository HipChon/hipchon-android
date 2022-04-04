package com.gritbus.hipchon.data.datasource.feed

import com.gritbus.hipchon.data.api.feed.FeedService
import com.gritbus.hipchon.data.model.feed.FeedAllData
import com.gritbus.hipchon.data.model.feed.FeedAllDataItem
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

    override suspend fun getFeedDetailData(userId: Int, postId: Int): Result<FeedAllDataItem> {
        return try {
            val data = feedService.getFeedDetailData(userId, postId)
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

    override suspend fun getFeedWithPlaceAllData(userId: Int, placeId: Int): Result<FeedWithPlaceAllData> {
        return try {
            val data = feedService.getFeedWithPlaceAllData(userId, placeId)
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

    override suspend fun savePost(userId: Int, postId: Int): Result<Int> {
        return try {
            val data = feedService.savePost(userId, postId)
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

    override suspend fun deletePost(userId: Int, postId: Int): Result<Unit> {
        return try {
            val data = feedService.deletePost(userId, postId)
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
