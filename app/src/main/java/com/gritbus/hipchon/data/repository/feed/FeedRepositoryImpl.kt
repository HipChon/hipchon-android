package com.gritbus.hipchon.data.repository.feed

import android.net.Uri
import com.gritbus.hipchon.data.datasource.feed.FeedDataSource
import com.gritbus.hipchon.data.model.feed.FeedAllData
import com.gritbus.hipchon.data.model.feed.FeedAllDataItem
import com.gritbus.hipchon.data.model.feed.FeedBestAllData
import com.gritbus.hipchon.data.model.feed.FeedCreateData
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

    override suspend fun getFeedDetailData(userId: Int, postId: Int): Result<FeedAllDataItem> {
        val result = feedDataSource.getFeedDetailData(userId, postId)

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

    override suspend fun getFeedWithPlaceAllData(userId: Int, placeId: Int): Result<FeedWithPlaceAllData> {
        val result = feedDataSource.getFeedWithPlaceAllData(userId, placeId)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override suspend fun savePost(userId: Int, postId: Int): Result<Int> {
        val result = feedDataSource.savePost(userId, postId)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override suspend fun deletePostLike(userId: Int, postId: Int): Result<Unit> {
        val result = feedDataSource.deletePostLike(userId, postId)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override suspend fun sendPost(fileList: List<Uri>, post: FeedCreateData): Result<Int> {
        val result = feedDataSource.sendPost(fileList, post)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }
}
