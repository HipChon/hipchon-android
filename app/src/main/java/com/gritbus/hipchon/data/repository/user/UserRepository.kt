package com.gritbus.hipchon.data.repository.user

import com.gritbus.hipchon.data.model.user.UserInfoData

interface UserRepository {

    suspend fun signupUser(userDto: UserInfoData): Result<String>

    suspend fun loginUser(loginType: String, loginId: String): Result<String>

    suspend fun updateProfile(userDto: UserInfoData): Result<String>

    suspend fun getUserData(loginType: String, loginId: String): Result<UserInfoData>

    suspend fun deleteUserData(loginType: String, loginId: String): Result<Unit>

    fun setAutoLoginId(value: String?)

    fun getAutoLoginId(): String?

    fun setAutoLoginPlatform(value: String?)

    fun getAutoLoginPlatform(): String?
}
