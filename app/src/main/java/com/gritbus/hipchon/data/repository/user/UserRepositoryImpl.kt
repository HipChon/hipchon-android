package com.gritbus.hipchon.data.repository.user

import com.gritbus.hipchon.data.datasource.user.UserDataSource
import com.gritbus.hipchon.data.model.user.UserInfoData
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
): UserRepository {

    override suspend fun signupUser(userDto: UserInfoData): Result<String> {
        val result = userDataSource.signupUser(userDto)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override suspend fun loginUser(loginType: String, loginId: String): Result<String> {
        val result = userDataSource.loginUser(loginType, loginId)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override suspend fun updateProfile(userDto: UserInfoData): Result<String> {
        val result = userDataSource.updateProfile(userDto)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override suspend fun getUserData(loginType: String, loginId: String): Result<UserInfoData> {
        val result = userDataSource.getUserData(loginType, loginId)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override suspend fun deleteUserData(loginType: String, loginId: String): Result<Void> {
        val result = userDataSource.deleteUserData(loginType, loginId)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }
}
