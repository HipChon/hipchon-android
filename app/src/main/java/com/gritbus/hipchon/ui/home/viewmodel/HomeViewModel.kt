package com.gritbus.hipchon.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.UserData
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
        val tempUrl = "https://user-images.githubusercontent.com/64943924/161104285-89d069d8-6a9d-42e3-afa4-f44a68b3dd1f.png"
        _bannerAllData.value = listOf(tempUrl)
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
        _weeklyHipPlaceAllData.value = _weeklyHipPlaceAllData.value?.map {
            if (it.placeId == selectedPlaceData.placeId) {
                it.copy(isMyplace = !it.isMyplace)
            } else {
                it
            }
        }
        // TODO 서버에 SAVE 업데이트
        // getWeeklyHipPlaceAllData()
    }
}
