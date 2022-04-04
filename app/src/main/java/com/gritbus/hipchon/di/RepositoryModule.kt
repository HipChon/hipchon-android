package com.gritbus.hipchon.di

import com.gritbus.hipchon.data.datasource.event.EventDataSource
import com.gritbus.hipchon.data.datasource.feed.CommentDataSource
import com.gritbus.hipchon.data.datasource.feed.FeedDataSource
import com.gritbus.hipchon.data.datasource.my.MyDataSource
import com.gritbus.hipchon.data.datasource.place.PlaceDataSource
import com.gritbus.hipchon.data.datasource.user.UserDataSource
import com.gritbus.hipchon.data.repository.event.EventRepository
import com.gritbus.hipchon.data.repository.event.EventRepositoryImpl
import com.gritbus.hipchon.data.repository.feed.CommentRepository
import com.gritbus.hipchon.data.repository.feed.CommentRepositoryImpl
import com.gritbus.hipchon.data.repository.feed.FeedRepository
import com.gritbus.hipchon.data.repository.feed.FeedRepositoryImpl
import com.gritbus.hipchon.data.repository.my.MyRepository
import com.gritbus.hipchon.data.repository.my.MyRepositoryImpl
import com.gritbus.hipchon.data.repository.place.PlaceRepository
import com.gritbus.hipchon.data.repository.place.PlaceRepositoryImpl
import com.gritbus.hipchon.data.repository.user.UserRepository
import com.gritbus.hipchon.data.repository.user.UserRepositoryImpl
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
    fun provideCommentRepository(commentDataSource: CommentDataSource): CommentRepository {
        return CommentRepositoryImpl(commentDataSource)
    }

    @Provides
    @Singleton
    fun provideMyRepository(myDataSource: MyDataSource): MyRepository {
        return MyRepositoryImpl(myDataSource)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDataSource: UserDataSource): UserRepository {
        return UserRepositoryImpl(userDataSource)
    }

    @Provides
    @Singleton
    fun provideEventRepository(eventDataSource: EventDataSource): EventRepository {
        return EventRepositoryImpl(eventDataSource)
    }
}
