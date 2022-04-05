package com.gritbus.hipchon.di

import android.content.Context
import com.gritbus.hipchon.data.api.event.EventService
import com.gritbus.hipchon.data.api.feed.CommentService
import com.gritbus.hipchon.data.api.feed.FeedService
import com.gritbus.hipchon.data.api.my.MyService
import com.gritbus.hipchon.data.api.place.PlaceService
import com.gritbus.hipchon.data.api.user.UserService
import com.gritbus.hipchon.data.datasource.event.EventDataSource
import com.gritbus.hipchon.data.datasource.event.EventDataSourceImpl
import com.gritbus.hipchon.data.datasource.feed.CommentDataSource
import com.gritbus.hipchon.data.datasource.feed.CommentDatasourceImpl
import com.gritbus.hipchon.data.datasource.feed.FeedDataSource
import com.gritbus.hipchon.data.datasource.feed.FeedDataSourceImpl
import com.gritbus.hipchon.data.datasource.my.MyDataSource
import com.gritbus.hipchon.data.datasource.my.MyDataSourceImpl
import com.gritbus.hipchon.data.datasource.place.PlaceDataSource
import com.gritbus.hipchon.data.datasource.place.PlaceDataSourceImpl
import com.gritbus.hipchon.data.datasource.user.AutoLoginManager
import com.gritbus.hipchon.data.datasource.user.UserDataSource
import com.gritbus.hipchon.data.datasource.user.UserDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideFeedDataSource(
        feedService: FeedService,
        @ApplicationContext context: Context
    ): FeedDataSource {
        return FeedDataSourceImpl(feedService, context)
    }

    @Provides
    @Singleton
    fun provideCommentDataSource(commentService: CommentService): CommentDataSource {
        return CommentDatasourceImpl(commentService)
    }

    @Provides
    @Singleton
    fun provideMyDataSource(myService: MyService): MyDataSource {
        return MyDataSourceImpl(myService)
    }

    @Provides
    @Singleton
    fun provideAutoLoginPlatformManager(@ApplicationContext context: Context): AutoLoginManager {
        return AutoLoginManager(context)
    }

    @Provides
    @Singleton
    fun provideUserDataSource(
        userService: UserService,
        autoLoginManager: AutoLoginManager,
        @ApplicationContext context: Context
    ): UserDataSource {
        return UserDataSourceImpl(userService, autoLoginManager, context)
    }

    @Provides
    @Singleton
    fun provideEventDataSource(eventService: EventService): EventDataSource {
        return EventDataSourceImpl(eventService)
    }
}
