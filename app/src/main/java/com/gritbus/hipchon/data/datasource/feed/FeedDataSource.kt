package com.gritbus.hipchon.data.datasource.feed

import android.net.Uri
import com.gritbus.hipchon.data.model.feed.FeedAllData
import com.gritbus.hipchon.data.model.feed.FeedAllDataItem
import com.gritbus.hipchon.data.model.feed.FeedBestAllData
import com.gritbus.hipchon.data.model.feed.FeedCreateData
import com.gritbus.hipchon.data.model.feed.FeedWithPlaceAllData

interface FeedDataSource {

    suspend fun getFeedAllData(userId: Int, order: String): Result<FeedAllData>

    suspend fun getFeedDetailData(userId: Int, postId: Int): Result<FeedAllDataItem>

    suspend fun getFeedBestAllData(): Result<FeedBestAllData>

    suspend fun getFeedWithPlaceAllData(userId: Int, placeId: Int): Result<FeedWithPlaceAllData>

    suspend fun savePost(userId: Int, postId: Int): Result<Int>

    suspend fun deletePostLike(userId: Int, postId: Int): Result<Unit>

    suspend fun sendPost(fileList: List<Uri>, post: FeedCreateData): Result<Int>

    suspend fun deletePost(userId: Int, postId: Int): Result<Unit>

    fun getFeedReportAllData(): ArrayList<Int>?

    fun setFeedReportAllData(reportAllData: ArrayList<Int>?)
}
