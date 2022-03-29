package com.gritbus.hipchon.data.repository.user

import com.gritbus.hipchon.data.model.user.UserInfoData

interface UserRepository {

    fun signupUser(userDto: UserInfoData): Result<Int>

    fun loginUser(loginType: String, loginId: Int): Result<Int>

    fun updateProfile(userDto: UserInfoData): Result<Int>

    fun getUserData(loginType: String, loginId: Int): Result<UserInfoData>

    fun deleteUserData(loginType: String, loginId: Int): Result<Void>
}
