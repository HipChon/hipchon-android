package com.gritbus.hipchon.data.datasource.feed

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.google.gson.Gson
import com.gritbus.hipchon.data.api.feed.FeedService
import com.gritbus.hipchon.data.model.feed.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import java.io.File
import javax.inject.Inject


class FeedDataSourceImpl @Inject constructor(
    private val feedService: FeedService,
    private val context: Context,
    private val feedReportManager: FeedReportManager
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

    override suspend fun getFeedWithPlaceAllData(
        userId: Int,
        placeId: Int
    ): Result<FeedWithPlaceAllData> {
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

    override suspend fun deletePostLike(userId: Int, postId: Int): Result<Unit> {
        return try {
            val data = feedService.deletePostLike(userId, postId)
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

    override suspend fun sendPost(fileList: List<Uri>, post: FeedCreateData): Result<Int> {
        return try {
            val data = feedService.sendPost(uriListToMultipart(fileList), postInfoToMultipart(post))
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

    private fun uriListToMultipart(filePathList: List<Uri>): ArrayList<MultipartBody.Part> {
        val files: ArrayList<MultipartBody.Part> = ArrayList()
        if (filePathList.isNullOrEmpty()) {
            files.add(
                MultipartBody.Part.createFormData(
                    "file",
                    "",
                    "".toRequestBody(MultipartBody.FORM)
                )
            )
            return files
        }
        for (i in filePathList.indices) {
            // Uri ????????? ??????????????? ????????? RequestBody ?????? ??????
            val fileBody =
                File(getPathFromUri(filePathList[i])).asRequestBody("image/jpeg".toMediaTypeOrNull())

            // ?????? ?????? ??????
            val fileName = "photo$i.jpg"
            // RequestBody??? Multipart.Part ?????? ??????
            val filePart = MultipartBody.Part.createFormData("file", fileName, fileBody)

            // ??????
            files.add(filePart)
        }
        return files
    }

    @SuppressLint("Range")
    fun getPathFromUri(uri: Uri): String {
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.moveToNext()

        return (cursor?.getString(cursor.getColumnIndex("_data")) ?: cursor?.close()) as String
    }

    private fun postInfoToMultipart(post: FeedCreateData): RequestBody {
        return Gson().toJson(post)
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
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

    override fun getFeedReportAllData(): ArrayList<Int>? {
        return feedReportManager.getFeedReportAllData()?.let { feedReportAllData ->
            val jsonArray = JSONArray(feedReportAllData)
            val reportArrayList = ArrayList<Int>()
            for (i in 0 until jsonArray.length()) {
                reportArrayList.add(jsonArray.optInt(i))
            }
            reportArrayList
        }
    }

    override fun setFeedReportAllData(reportAllData: ArrayList<Int>?) {
        val jsonArray = JSONArray()
        reportAllData?.let {
            for (i in 0 until it.size) {
                jsonArray.put(it[i])
            }
        }
        feedReportManager.setFeedReportAllData(jsonArray.toString())
    }
}
