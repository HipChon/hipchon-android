package com.gritbus.hipchon.data.model.event

import java.io.Serializable

class EventAllData : ArrayList<EventAllDataItem>()

data class EventAllDataItem(
    val detail: String,
    val eventId: Int,
    val thumbnail: String,
    val time: String,
    val title: String,
    val url: String
): Serializable
