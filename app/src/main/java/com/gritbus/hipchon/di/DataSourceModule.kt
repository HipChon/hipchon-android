package com.gritbus.hipchon.di

import com.gritbus.hipchon.data.api.feed.FeedService
import com.gritbus.hipchon.data.api.my.MyService
import com.gritbus.hipchon.data.api.place.PlaceService
import com.gritbus.hipchon.data.api.user.UserService
import com.gritbus.hipchon.data.datasource.feed.FeedDataSource
import com.gritbus.hipchon.data.datasource.feed.FeedDataSourceImpl
import com.gritbus.hipchon.data.datasource.my.MyDataSource
import com.gritbus.hipchon.data.datasource.my.MyDataSourceImpl
import com.gritbus.hipchon.data.datasource.place.PlaceDataSource
import com.gritbus.hipchon.data.datasource.place.PlaceDataSourceImpl
import com.gritbus.hipchon.data.datasource.user.UserDataSource
import com.gritbus.hipchon.data.datasource.user.UserDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun providePlaceDataSource(placeService: PlaceService): PlaceDataSource {
        return PlaceDataSourceImpl(placeService)
    }

    @Provides
    @Singleton
    fun provideFeedDataSource(feedService: FeedService): FeedDataSource {
        return FeedDataSourceImpl(feedService)
    }

    @Provides
    @Singleton
    fun provideMyDataSource(myService: MyService): MyDataSource {
        return MyDataSourceImpl(myService)
    }

    @Provides
    @Singleton
    fun provideUserDataSource(userService: UserService): UserDataSource {
        return UserDataSourceImpl(userService)
    }
}
