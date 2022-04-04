package com.gritbus.hipchon.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.UserData
import com.gritbus.hipchon.data.model.feed.FeedAllDataItem
import com.gritbus.hipchon.data.model.feed.FeedBestAllDataItem
import com.gritbus.hipchon.data.model.place.LocalHipsterAllDataItem
import com.gritbus.hipchon.data.model.place.PlaceHipSearchAllDataItem
import com.gritbus.hipchon.data.repository.feed.FeedRepository
import com.gritbus.hipchon.data.repository.place.PlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val placeRepository: PlaceRepository,
    private val feedRepository: FeedRepository
) : ViewModel() {

    private val _localHipsterAllData = MutableLiveData<List<LocalHipsterAllDataItem>>()
    val localHipsterAllData: LiveData<List<LocalHipsterAllDataItem>> = _localHipsterAllData

    private val _bannerAllData = MutableLiveData<List<String>>()
    val bannerAllData: LiveData<List<String>> = _bannerAllData

    private val _bestFeedAllData = MutableLiveData<List<FeedBestAllDataItem>>()
    val bestFeedAllData: LiveData<List<FeedBestAllDataItem>> = _bestFeedAllData

    private val _weeklyHipPlaceAllData = MutableLiveData<List<PlaceHipSearchAllDataItem>>()
    val weeklyHipPlaceAllData: LiveData<List<PlaceHipSearchAllDataItem>> = _weeklyHipPlaceAllData

    private val _currentBestFeedData = MutableLiveData<FeedAllDataItem>()
    val currentBestFeedData: LiveData<FeedAllDataItem> = _currentBestFeedData

    fun getLocalHipsterAllData() {
        viewModelScope.launch {
            placeRepository.getLocalHipsterAllData()
                .onSuccess {
                    _localHipsterAllData.value = it
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "local hipster error")
                }
        }
    }

    fun getBannerAllData() {
        _bannerAllData.value = fakeBannerAllData
    }

    fun getBestFeedAllData() {
        viewModelScope.launch {
            feedRepository.getFeedBestAllData()
                .onSuccess { _bestFeedAllData.value = it }
                .onFailure { Log.e(this.javaClass.name, it.message ?: "best feed error") }
        }
    }

    fun getWeeklyHipPlaceAllData() {
        viewModelScope.launch {
            placeRepository.getPlaceHipSearchAllData(UserData.userId)
                .onSuccess { _weeklyHipPlaceAllData.value = it }
                .onFailure { Log.e(this.javaClass.name, it.message ?: "hip place error") }
        }
    }

    fun updateSave(selectedPlaceData: PlaceHipSearchAllDataItem) {
        val isCurrentSaveState = selectedPlaceData.isMyplace
        viewModelScope.launch {
            when (isCurrentSaveState) {
                true -> {
                    placeRepository.deletePlace(UserData.userId, selectedPlaceData.placeId)
                        .onSuccess {
                            getWeeklyHipPlaceAllData()
                        }
                        .onFailure {
                            Log.e(this.javaClass.name, it.message ?: "hip place delete error")
                        }
                }
                false -> {
                    placeRepository.savePlace(UserData.userId, selectedPlaceData.placeId)
                        .onSuccess {
                            getWeeklyHipPlaceAllData()
                        }
                        .onFailure {
                            Log.e(this.javaClass.name, it.message ?: "hip place save error")
                        }
                }
            }
        }
    }

    fun getCurrentBestFeedData(postId: Int) {
        viewModelScope.launch {
            feedRepository.getFeedDetailData(UserData.userId, postId)
                .onSuccess {
                    _currentBestFeedData.value = it
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "current feed error")
                }
        }
    }

    // 서버 연결시 FAKE 데이터 삭제
    private val fakeBannerUrl =
        "https://user-images.githubusercontent.com/64943924/156609871-0f94812a-286d-4120-989c-91e8ddbb1086.png"

    private val fakeBannerAllData: List<String> = listOf(
        fakeBannerUrl,
        fakeBannerUrl,
        fakeBannerUrl,
        fakeBannerUrl,
        fakeBannerUrl,
        fakeBannerUrl,
        fakeBannerUrl,
        fakeBannerUrl
    )
}
