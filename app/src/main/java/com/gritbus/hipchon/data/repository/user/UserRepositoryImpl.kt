package com.gritbus.hipchon.data.repository.user

import com.gritbus.hipchon.data.datasource.user.UserDataSource
import com.gritbus.hipchon.data.model.user.UserInfoData
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
): UserRepository {

    override fun signupUser(userDto: UserInfoData): Result<Int> {
        val result = userDataSource.signupUser(userDto)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override fun loginUser(loginType: String, loginId: Int): Result<Int> {
        val result = userDataSource.loginUser(loginType, loginId)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override fun updateProfile(userDto: UserInfoData): Result<Int> {
        val result = userDataSource.updateProfile(userDto)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override fun getUserData(loginType: String, loginId: Int): Result<UserInfoData> {
        val result = userDataSource.getUserData(loginType, loginId)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override fun deleteUserData(loginType: String, loginId: Int): Result<Void> {
        val result = userDataSource.deleteUserData(loginType, loginId)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }
}
