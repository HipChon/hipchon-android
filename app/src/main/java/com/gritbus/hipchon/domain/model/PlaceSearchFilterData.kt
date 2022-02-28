package com.gritbus.hipchon.domain.model

import java.io.Serializable

data class PlaceSearchFilterData(
    val personCount: Int,
    val withAnimal: Boolean,
    val area: Area,
    val hashtag: Hashtag
): Serializable
