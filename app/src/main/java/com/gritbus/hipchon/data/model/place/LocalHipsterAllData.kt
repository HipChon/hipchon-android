package com.gritbus.hipchon.data.model.place

class LocalHipsterAllData : ArrayList<LocalHipsterAllDataItem>()

data class LocalHipsterAllDataItem(
    val city: String,
    val hipsterId: Int,
    val image: String,
    val summary: String,
    val title: String
)
