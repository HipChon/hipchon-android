package com.gritbus.hipchon.data.datasource.user

import android.net.Uri
import com.gritbus.hipchon.data.model.user.UserDataForUpdate
import com.gritbus.hipchon.data.model.user.UserInfoData

interface UserDataSource {

    suspend fun signupUser(file: Uri?, user: UserDataForUpdate): Result<String>

    suspend fun loginUser(loginType: String, loginId: String): Result<String>

    suspend fun updateProfile(file: Uri?, user: UserDataForUpdate): Result<String>

    suspend fun getUserData(loginType: String, loginId: String): Result<UserInfoData>

    suspend fun deleteUserData(loginType: String, loginId: String): Result<Unit>

    fun setAutoLoginId(value: String?)

    fun getAutoLoginId(): String?

    fun setAutoLoginPlatform(value: String?)

    fun getAutoLoginPlatform(): String?

    fun setUserReportAllData(reportAllData: ArrayList<Int>?)

    fun getUserReportAllData(): ArrayList<Int>?
}
