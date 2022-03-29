package com.gritbus.hipchon.data.datasource.user

import com.gritbus.hipchon.data.api.user.UserService
import com.gritbus.hipchon.data.model.user.UserInfoData
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService
): UserDataSource {

    override fun signupUser(userDto: UserInfoData): Result<Int> {
        return try {
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

    override fun loginUser(loginType: String, loginId: Int): Result<Int> {
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

    override fun updateProfile(userDto: UserInfoData): Result<Int> {
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

    override fun getUserData(loginType: String, loginId: Int): Result<UserInfoData> {
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

    override fun deleteUserData(loginType: String, loginId: Int): Result<Void> {
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
}
