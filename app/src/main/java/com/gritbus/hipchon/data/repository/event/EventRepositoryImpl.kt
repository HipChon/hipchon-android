package com.gritbus.hipchon.data.repository.event

import com.gritbus.hipchon.data.datasource.event.EventDataSource
import com.gritbus.hipchon.data.model.event.EventAllData
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventDataSource: EventDataSource
): EventRepository {

    override suspend fun getEventAllData(): Result<EventAllData> {
        val result = eventDataSource.getEventAllData()

        return if (result.exceptionOrNull() is Exception) {
            Result.failure(result.exceptionOrNull() as Exception)
        } else {
            result
        }
    }
}
