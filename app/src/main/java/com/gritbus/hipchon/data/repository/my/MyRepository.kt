package com.gritbus.hipchon.data.repository.my

import com.gritbus.hipchon.data.model.my.MyCommentAllData
import com.gritbus.hipchon.data.model.my.MyFeedAllData
import com.gritbus.hipchon.data.model.my.MyLikeFeedAllData
import com.gritbus.hipchon.data.model.my.MyPlaceAllData

interface MyRepository {

    suspend fun getMyFeedAllData(userId: Int): Result<MyFeedAllData>

    suspend fun getMyLikeFeedAllData(userId: Int): Result<MyLikeFeedAllData>

    suspend fun getMyPlace(userId: Int): Result<MyPlaceAllData>

    suspend fun getMyPlaceWithCategory(userId: Int, category: Int): Result<MyPlaceAllData>

    suspend fun getMyComment(userId: Int): Result<MyCommentAllData>
}
