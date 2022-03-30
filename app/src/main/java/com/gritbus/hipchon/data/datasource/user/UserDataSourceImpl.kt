package com.gritbus.hipchon.data.datasource.user

import android.util.Log
import com.gritbus.hipchon.data.api.user.UserService
import com.gritbus.hipchon.data.model.user.UserInfoData
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService,
    private val authoLoginManager: AutoLoginManager
) : UserDataSource {

    override suspend fun signupUser(userDto: UserInfoData): Result<String> {
        return try {
            Log.i("userdto", userDto.toString())
            val data = userService.signupUser(userDto)
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

    override suspend fun deleteUserData(loginType: String, loginId: String): Result<Void> {
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
