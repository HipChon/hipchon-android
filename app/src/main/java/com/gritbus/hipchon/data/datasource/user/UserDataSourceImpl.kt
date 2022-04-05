package com.gritbus.hipchon.data.datasource.user

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.google.gson.Gson
import com.gritbus.hipchon.data.api.user.UserService
import com.gritbus.hipchon.data.model.user.UserDataForUpdate
import com.gritbus.hipchon.data.model.user.UserInfoData
import com.gritbus.hipchon.domain.mapper.ErrorBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService,
    private val authoLoginManager: AutoLoginManager,
    private val context: Context
) : UserDataSource {

    override suspend fun signupUser(file: Uri?, user: UserDataForUpdate): Result<String> {
        return try {
            val data = userService.signupUser(uriToMultipart(file), userInfoToMultipart(user))
            if (data.isSuccessful) {
                data.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Throwable(Gson().fromJson(data.errorBody()?.string(), ErrorBody::class.java).message))
            } else {
                Result.failure(Throwable(Gson().fromJson(data.errorBody()?.string(), ErrorBody::class.java).message))
            }
        } catch (e: Exception) {
            Result.failure(Throwable(e.message))
        }
    }

    private fun uriToMultipart(filePath: Uri?): MultipartBody.Part {
        return if (filePath == null){
            MultipartBody.Part.createFormData("file", "", "".toRequestBody(MultipartBody.FORM))
        } else {
            val fileBody =File(getPathFromUri(filePath)).asRequestBody("image/jpeg".toMediaTypeOrNull())
            val fileName = "profile.jpg"

            MultipartBody.Part.createFormData("file", fileName, fileBody)
        }
    }

    @SuppressLint("Range")
    fun getPathFromUri(uri: Uri): String {
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.moveToNext()

        return (cursor?.getString(cursor.getColumnIndex("_data")) ?: cursor?.close()) as String
    }

    private fun userInfoToMultipart(user: UserDataForUpdate): RequestBody {
        return Gson().toJson(user).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    override suspend fun loginUser(loginType: String, loginId: String): Result<String> {
        return try {
            val data = userService.loginUser(loginType, loginId)
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

    override suspend fun updateProfile(userDto: UserInfoData): Result<String> {
        return try {
            val data = userService.updateProfile(userDto)
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

    override suspend fun getUserData(loginType: String, loginId: String): Result<UserInfoData> {
        return try {
            val data = userService.getUserData(loginType, loginId)
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

    override suspend fun deleteUserData(loginType: String, loginId: String): Result<Unit> {
        return try {
            val data = userService.deleteUserData(loginType, loginId)
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

    override fun setAutoLoginId(value: String?) {
        authoLoginManager.setLoginId(value)
    }

    override fun getAutoLoginId(): String? {
        return authoLoginManager.getLoginId()
    }

    override fun setAutoLoginPlatform(value: String?) {
        authoLoginManager.setPlatform(value)
    }

    override fun getAutoLoginPlatform(): String? {
        return authoLoginManager.getPlatform()
    }
}
