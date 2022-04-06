package com.gritbus.hipchon.ui.feed.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.UserData
import com.gritbus.hipchon.data.model.feed.FeedAllDataItem
import com.gritbus.hipchon.data.model.feed.FeedPlaceItem
import com.gritbus.hipchon.data.repository.feed.FeedRepository
import com.gritbus.hipchon.data.repository.place.PlaceRepository
import com.gritbus.hipchon.domain.model.FeedOrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository,
    private val placeRepository: PlaceRepository
) : ViewModel() {

    private val _reviewAllData = MutableLiveData<List<FeedAllDataItem>>()
    val reviewAllData: LiveData<List<FeedAllDataItem>> = _reviewAllData

    private val _reviewOrderType = MutableLiveData(FeedOrderType.RECENT)
    val reviewOrderType: LiveData<FeedOrderType> = _reviewOrderType

    fun initData() {
        val orderType = _reviewOrderType.value?.let {
            if (it == FeedOrderType.LIKE) "like" else "recent"
        } ?: return

        viewModelScope.launch {
            feedRepository.getFeedAllData(UserData.userId, orderType)
                .onSuccess {
                    val reportFeedAllData = feedRepository.getFeedReportAllData()
                    _reviewAllData.value = it.filter { feed ->
                        if (reportFeedAllData != null){
                            !reportFeedAllData.contains(feed.postId)
                        } else {
                            true
                        }
                    }
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "review error")
                }
        }
    }

    fun setOrderType(orderType: FeedOrderType) {
        _reviewOrderType.value = orderType
    }

    fun likePost(postId: Int, isMypost: Boolean) {
        viewModelScope.launch {
            when(isMypost) {
                true -> {
                    feedRepository.deletePostLike(UserData.userId, postId)
                        .onSuccess {
                            initData()
                        }
                        .onFailure {
                            Log.e(this.javaClass.name, it.message ?: "post like error")
                        }
                }
                false -> {
                    feedRepository.savePost(UserData.userId, postId)
                        .onSuccess {
                            initData()
                        }
                        .onFailure {
                            Log.e(this.javaClass.name, it.message ?: "post like error")
                        }
                }
            }
        }
    }

    fun savePlace(placeData: FeedPlaceItem){
        viewModelScope.launch {
            when (placeData.isMyplace) {
                true -> {
                    placeRepository.deletePlace(UserData.userId, placeData.placeId)
                        .onSuccess {
                            initData()
                        }
                        .onFailure {
                            Log.e(this.javaClass.name, it.message ?: "place save error")
                        }
                }
                false -> {
                    placeRepository.savePlace(UserData.userId, placeData.placeId)
                        .onSuccess {
                            initData()
                        }
                        .onFailure {
                            Log.e(this.javaClass.name, it.message ?: "place save error")
                        }
                }
            }
        }
    }

    fun reportFeed(postId: Int) {
        feedRepository.setFeedReportAllData(
            feedRepository.getFeedReportAllData()?.apply {
                add(postId)
            }
        )
        initData()
    }
}
