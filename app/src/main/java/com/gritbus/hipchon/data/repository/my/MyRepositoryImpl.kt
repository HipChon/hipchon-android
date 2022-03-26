package com.gritbus.hipchon.data.repository.my

import com.gritbus.hipchon.data.datasource.my.MyDataSource
import com.gritbus.hipchon.data.model.my.MyFeedAllData
import com.gritbus.hipchon.data.model.my.MyLikeFeedAllData
import com.gritbus.hipchon.data.model.my.MyPlaceAllData
import com.gritbus.hipchon.data.model.my.MyPlaceSaveData
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val myDataSource: MyDataSource
) : MyRepository {

    override suspend fun getMyFeedAllData(userId: Int): Result<MyFeedAllData> {
        val result = myDataSource.getMyFeedAllData(userId)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override suspend fun getMyLikeFeedAllData(userId: Int): Result<MyLikeFeedAllData> {
        val result = myDataSource.getMyLikeFeedAllData(userId)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override suspend fun getMyPlace(userId: Int): Result<MyPlaceAllData> {
        val result = myDataSource.getMyPlace(userId)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override suspend fun saveMyPlace(userId: Int, placeId: Int): Result<MyPlaceSaveData> {
        val result = myDataSource.saveMyPlace(userId, placeId)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }
}
