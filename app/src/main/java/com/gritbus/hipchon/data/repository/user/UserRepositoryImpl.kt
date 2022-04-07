package com.gritbus.hipchon.data.repository.user

import android.net.Uri
import com.gritbus.hipchon.data.datasource.user.UserDataSource
import com.gritbus.hipchon.data.model.user.UserDataForUpdate
import com.gritbus.hipchon.data.model.user.UserInfoData
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
): UserRepository {

    override suspend fun signupUser(file: Uri?, user: UserDataForUpdate): Result<String> {
        val result = userDataSource.signupUser(file, user)

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

    override suspend fun updateProfile(file: Uri?, user: UserDataForUpdate): Result<String> {
        val result = userDataSource.updateProfile(file, user)

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

    override suspend fun deleteUserData(loginType: String, loginId: String): Result<Unit> {
        val result = userDataSource.deleteUserData(loginType, loginId)

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }

    override fun setAutoLoginId(value: String?) {
        userDataSource.setAutoLoginId(value)
    }

    override fun getAutoLoginId(): String? {
        return userDataSource.getAutoLoginId()
    }

    override fun setAutoLoginPlatform(value: String?) {
        userDataSource.setAutoLoginPlatform(value)
    }

    override fun getAutoLoginPlatform(): String? {
        return userDataSource.getAutoLoginPlatform()
    }

    override fun setUserReportAllData(reportAllData: ArrayList<Int>?) {
        userDataSource.setUserReportAllData(reportAllData)
    }

    override fun getUserReportAllData(): ArrayList<Int>? {
        return userDataSource.getUserReportAllData()
    }
}
