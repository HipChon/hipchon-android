package com.gritbus.hipchon.data.model.user

import java.io.Serializable

data class UserDataForUpdate(
    val loginId: String,
    val loginType: String,
    val name: String,
    val email: String,
    val isMarketing: Boolean
) : Serializable
