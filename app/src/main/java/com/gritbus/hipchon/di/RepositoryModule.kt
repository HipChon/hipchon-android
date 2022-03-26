package com.gritbus.hipchon.di

import com.gritbus.hipchon.data.datasource.feed.FeedDataSource
import com.gritbus.hipchon.data.datasource.my.MyDataSource
import com.gritbus.hipchon.data.datasource.place.PlaceDataSource
import com.gritbus.hipchon.data.repository.feed.FeedRepository
import com.gritbus.hipchon.data.repository.feed.FeedRepositoryImpl
import com.gritbus.hipchon.data.repository.my.MyRepository
import com.gritbus.hipchon.data.repository.my.MyRepositoryImpl
import com.gritbus.hipchon.data.repository.place.PlaceRepository
import com.gritbus.hipchon.data.repository.place.PlaceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePlaceRepository(placeDataSource: PlaceDataSource): PlaceRepository {
        return PlaceRepositoryImpl(placeDataSource)
    }

    @Provides
    @Singleton
    fun provideFeedRepository(feedDataSource: FeedDataSource): FeedRepository {
        return FeedRepositoryImpl(feedDataSource)
    }

    @Provides
    @Singleton
    fun provideMyRepository(myDataSource: MyDataSource): MyRepository {
        return MyRepositoryImpl(myDataSource)
    }
}
