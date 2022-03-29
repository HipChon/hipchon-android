package com.gritbus.hipchon.data.model.user

data class UserInfoData(
    val email: String,
    val id: Int,
    val isMarketing: Boolean,
    val loginId: Int,
    val loginType: String,
    val name: String,
    val profileImage: String
)
