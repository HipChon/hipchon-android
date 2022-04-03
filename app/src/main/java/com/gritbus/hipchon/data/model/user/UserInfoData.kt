package com.gritbus.hipchon.data.model.user

data class UserInfoData(
    val email: String,
    val image: String,
    val isMarketing: Boolean,
    val loginId: String,
    val loginType: String,
    val name: String,
    val userId: Int
)
