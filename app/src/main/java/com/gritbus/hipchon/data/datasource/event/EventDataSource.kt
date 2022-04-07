package com.gritbus.hipchon.data.datasource.event

import com.gritbus.hipchon.data.model.event.EventAllData

interface EventDataSource {

    suspend fun getEventAllData(): Result<EventAllData>
}
