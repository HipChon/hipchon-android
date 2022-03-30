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
    suspend fun signupUser(
        @Body userDto: UserInfoData
    ): Response<String>

    @GET("user/login/{login_type}/{login_id}")
    suspend fun loginUser(
        @Path("login_type") loginType: String,
        @Path("login_id") loginId: String
    ): Response<String>

    @PUT("user")
    suspend fun updateProfile(
        @Body userDto: UserInfoData
    ): Response<String>

    @GET("user/{login_type}/{login_id}")
    suspend fun getUserData(
        @Path("login_type") loginType: String,
        @Path("login_id") loginId: String
    ): Response<UserInfoData>

    @DELETE("user/{login_type}/{login_id}")
    suspend fun deleteUserData(
        @Path("login_type") loginType: String,
        @Path("login_id") loginId: String
    ): Response<Void>
}
