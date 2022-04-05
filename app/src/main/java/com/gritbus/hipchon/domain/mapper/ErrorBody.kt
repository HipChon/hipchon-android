package com.gritbus.hipchon.domain.mapper

data class ErrorBody(
    val status: Int,
    val message: String,
    val code: String
)
