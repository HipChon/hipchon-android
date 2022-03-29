package com.gritbus.hipchon.data.model

import kotlin.properties.Delegates

object UserData {

    var userId by Delegates.notNull<Int>()
    var userLoginId by Delegates.notNull<Int>()
}
