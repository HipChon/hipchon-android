package com.gritbus.hipchon.data.datasource.my

import com.gritbus.hipchon.data.api.my.MyService
import com.gritbus.hipchon.data.model.my.MyCommentAllData
import com.gritbus.hipchon.data.model.my.MyFeedAllData
import com.gritbus.hipchon.data.model.my.MyLikeFeedAllData
import com.gritbus.hipchon.data.model.my.MyPlaceAllData
import javax.inject.Inject

class MyDataSourceImpl @Inject constructor(
    private val myService: MyService
): MyDataSource {

    override suspend fun getMyFeedAllData(userId: Int): Result<MyFeedAllData> {
        return try {
            val data = myService.getMyFeedAllData(userId)
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

    override suspend fun getMyLikeFeedAllData(userId: Int): Result<MyLikeFeedAllData> {
        return try {
            val data = myService.getMyLikeFeedAllData(userId)
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

    override suspend fun getMyPlace(userId: Int): Result<MyPlaceAllData> {
        return try {
            val data = myService.getMyPlace(userId)
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

    override suspend fun getMyPlaceWithCategory(
        userId: Int,
        category: Int
    ): Result<MyPlaceAllData> {
        return try {
            val data = myService.getMyPlaceWithCategory(userId, category)
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

    override suspend fun getMyComment(userId: Int): Result<MyCommentAllData> {
        return try {
            val data = myService.getMyComment(userId)
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
