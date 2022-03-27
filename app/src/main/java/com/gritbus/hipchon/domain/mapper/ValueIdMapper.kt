package com.gritbus.hipchon.domain.mapper

import com.gritbus.hipchon.domain.model.Area
import com.gritbus.hipchon.domain.model.Hashtag
import com.gritbus.hipchon.domain.model.Keyword
import com.gritbus.hipchon.domain.model.Type

fun keywordValueToId(keywordValue: String, keywordType: Keyword): Int {
    return (keywordType.keywordAll as List<*>).indexOf(keywordValue) + 1
}

fun areaValueToId(areaValue: String): Int {
    return Area.values().map { it.value }.indexOf(areaValue)
}

fun hashtagValueToId(hashtagValue: String): Int {
    return Hashtag.values().map { it.value }.indexOf(hashtagValue)
}

fun categoryValueToId(categoryValue: String): Int {
    return Type.values().map { it.value }.indexOf(categoryValue)
}
