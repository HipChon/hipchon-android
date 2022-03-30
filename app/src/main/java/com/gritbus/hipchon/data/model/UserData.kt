package com.gritbus.hipchon.data.model

import kotlin.properties.Delegates

object UserData {

    lateinit var platform: String
    var userId by Delegates.notNull<Int>()
    var userLoginId by Delegates.notNull<Int>()
}
