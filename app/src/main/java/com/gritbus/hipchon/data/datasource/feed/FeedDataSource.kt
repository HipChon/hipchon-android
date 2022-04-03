package com.gritbus.hipchon.data.datasource.feed

import com.gritbus.hipchon.data.model.feed.FeedAllData
import com.gritbus.hipchon.data.model.feed.FeedAllDataItem
import com.gritbus.hipchon.data.model.feed.FeedBestAllData
import com.gritbus.hipchon.data.model.feed.FeedWithPlaceAllData

interface FeedDataSource {

    suspend fun getFeedAllData(userId: Int, order: String): Result<FeedAllData>

    suspend fun getFeedDetailData(userId: Int, postId: Int): Result<FeedAllDataItem>

    suspend fun getFeedBestAllData(): Result<FeedBestAllData>

    suspend fun getFeedWithPlaceAllData(placeId: Int): Result<FeedWithPlaceAllData>

    suspend fun savePost(userId: Int, postId: Int): Result<Int>

    suspend fun deletePost(userId: Int, postId: Int): Result<Unit>
}
