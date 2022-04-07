package com.gritbus.hipchon.data.model

import kotlin.properties.Delegates

object UserData {

    lateinit var platform: String
    var userId by Delegates.notNull<Int>()
    lateinit var userLoginId: String
}
