package com.gritbus.hipchon.data.datasource.feed

import com.gritbus.hipchon.data.model.feed.FeedAllData
import com.gritbus.hipchon.data.model.feed.FeedBestAllData
import com.gritbus.hipchon.data.model.feed.FeedPersonalAllData
import com.gritbus.hipchon.data.model.feed.FeedWithPlaceAllData

interface FeedDataSource {

    suspend fun getFeedAllData(userId: Int, order: String): Result<FeedAllData>

    suspend fun getFeedBestAllData(): Result<FeedBestAllData>

    suspend fun getFeedWithPlaceAllData(placeId: Int): Result<FeedWithPlaceAllData>

    suspend fun getFeedPersonalAllData(userId: Int): Result<FeedPersonalAllData>
}
