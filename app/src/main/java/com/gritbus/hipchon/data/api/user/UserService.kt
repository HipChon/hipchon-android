package com.gritbus.hipchon.data.api.user

import com.gritbus.hipchon.data.model.user.UserInfoData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {

    @POST("user")
    fun signupUser(
        @Body userDto: UserInfoData
    ): Response<Int>

    @GET("user/login/{login_type}/{login_id}")
    fun loginUser(
        @Path("login_type") loginType: String,
        @Path("login_id") loginId: Int
    ): Response<Int>

    @PUT("user")
    fun updateProfile(
        @Body userDto: UserInfoData
    ): Response<Int>

    @GET("user/{login_type}/{login_id}")
    fun getUserData(
        @Path("login_type") loginType: String,
        @Path("login_id") loginId: Int
    ): Response<UserInfoData>

    @DELETE("user/{login_type}/{login_id}")
    fun deleteUserData(
        @Path("login_type") loginType: String,
        @Path("login_id") loginId: Int
    ): Response<Void>
}
