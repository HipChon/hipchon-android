package com.gritbus.hipchon.data.api.event

import com.gritbus.hipchon.data.model.event.EventAllData
import retrofit2.Response
import retrofit2.http.GET

interface EventService {

    @GET("event")
    suspend fun getEventAllData(): Response<EventAllData>
}
