package com.gritbus.hipchon.data.repository.event

import com.gritbus.hipchon.data.model.event.EventAllData

interface EventRepository {

    suspend fun getEventAllData(): Result<EventAllData>
}
