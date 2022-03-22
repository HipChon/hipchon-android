package com.gritbus.hipchon.domain.model

import java.io.Serializable

data class PlaceSearchFilterData(
    val area: Area = Area.ALL,
    val hashtag: Hashtag = Hashtag.NOTHING
): Serializable
