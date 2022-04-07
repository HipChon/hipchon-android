package com.gritbus.hipchon.ui.place.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.UserData
import com.gritbus.hipchon.data.model.feed.FeedAllDataItem
import com.gritbus.hipchon.data.model.place.PlaceDetailData
import com.gritbus.hipchon.data.repository.feed.FeedRepository
import com.gritbus.hipchon.data.repository.user.UserRepository
import com.gritbus.hipchon.ui.place.view.PlaceDetailActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceDetailFeedViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val feedRepository: FeedRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private lateinit var _placeData: PlaceDetailData

    private val _placeReviewAllData = MutableLiveData<List<FeedAllDataItem>>()
    val placeReviewAllData: LiveData<List<FeedAllDataItem>> = _placeReviewAllData

    init {
        savedStateHandle.get<PlaceDetailData>(PlaceDetailActivity.PLACE_DETAIL_FEED_MORE)?.let {
            _placeData = it
        }
    }

    fun getReviewData() {
        viewModelScope.launch {
            feedRepository.getFeedWithPlaceAllData(UserData.userId, _placeData.placeId)
                .onSuccess {
                    val reportFeedAllData = feedRepository.getFeedReportAllData()
                    val reportUserAllData = userRepository.getUserReportAllData()
                    _placeReviewAllData.value = it.filter { feed ->
                        if (reportFeedAllData != null){
                            if (reportUserAllData != null) {
                                !reportFeedAllData.contains(feed.postId) && !reportUserAllData.contains(feed.user.userId)
                            } else {
                                !reportFeedAllData.contains(feed.postId)
                            }
                        } else {
                            if (reportUserAllData != null) {
                                !reportUserAllData.contains(feed.user.userId)
                            } else {
                                true
                            }
                        }
                    }
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "place review more error")
                }
        }
    }

    fun likePost(postId: Int, isMypost: Boolean) {
        viewModelScope.launch {
            when(isMypost) {
                true -> {
                    feedRepository.deletePostLike(UserData.userId, postId)
                        .onSuccess {
                            getReviewData()
                        }
                        .onFailure {
                            Log.e(this.javaClass.name, it.message ?: "post like error")
                        }
                }
                false -> {
                    feedRepository.savePost(UserData.userId, postId)
                        .onSuccess {
                            getReviewData()
                        }
                        .onFailure {
                            Log.e(this.javaClass.name, it.message ?: "post like error")
                        }
                }
            }
        }
    }

    fun getPostData(): PlaceDetailData {
        return _placeData
    }

    fun reportFeed(postId: Int) {
        feedRepository.setFeedReportAllData(
            feedRepository.getFeedReportAllData()?.apply {
                add(postId)
            }
        )
        getReviewData()
    }

    fun reportUser(userId: Int) {
        userRepository.setUserReportAllData(
            userRepository.getUserReportAllData()?.apply {
                add(userId)
            }
        )
        getReviewData()
    }
}
