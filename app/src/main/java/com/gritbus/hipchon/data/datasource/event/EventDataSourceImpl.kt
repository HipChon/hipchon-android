package com.gritbus.hipchon.data.datasource.event

import com.gritbus.hipchon.data.api.event.EventService
import com.gritbus.hipchon.data.model.event.EventAllData
import javax.inject.Inject

class EventDataSourceImpl @Inject constructor(
    private val eventService: EventService
): EventDataSource {

    override suspend fun getEventAllData(): Result<EventAllData> {
        return try {
            val data = eventService.getEventAllData()
            if (data.isSuccessful) {
                data.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Throwable(data.message()))
            } else {
                Result.failure(Throwable(data.message()))
            }
        } catch (e: Exception) {
            Result.failure(Throwable(e.message))
        }
    }
}
