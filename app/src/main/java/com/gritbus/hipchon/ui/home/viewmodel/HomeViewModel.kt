package com.gritbus.hipchon.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.UserData
import com.gritbus.hipchon.data.model.feed.FeedBestAllDataItem
import com.gritbus.hipchon.data.model.place.PlaceHipSearchAllDataItem
import com.gritbus.hipchon.data.repository.feed.FeedRepository
import com.gritbus.hipchon.data.repository.place.PlaceRepository
import com.gritbus.hipchon.domain.model.Area
import com.gritbus.hipchon.domain.model.LocalHipsterData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val placeRepository: PlaceRepository,
    private val feedRepository: FeedRepository
) : ViewModel() {

    private val _localHipsterAllData = MutableLiveData<List<LocalHipsterData>>()
    val localHipsterAllData: LiveData<List<LocalHipsterData>> = _localHipsterAllData

    private val _bannerAllData = MutableLiveData<List<String>>()
    val bannerAllData: LiveData<List<String>> = _bannerAllData

    private val _bestFeedAllData = MutableLiveData<List<FeedBestAllDataItem>>()
    val bestFeedAllData: LiveData<List<FeedBestAllDataItem>> = _bestFeedAllData

    private val _weeklyHipPlaceAllData = MutableLiveData<List<PlaceHipSearchAllDataItem>>()
    val weeklyHipPlaceAllData: LiveData<List<PlaceHipSearchAllDataItem>> = _weeklyHipPlaceAllData

    fun getLocalHipsterAllData() {
        _localHipsterAllData.value = fakeLocalHipsterAllData
    }

    fun getBannerAllData() {
        _bannerAllData.value = fakeBannerAllData
    }

    fun getBestFeedAllData() {
        viewModelScope.launch {
            feedRepository.getFeedBestAllData()
                .onSuccess { _bestFeedAllData.value = it.data }
                .onFailure { Log.e(this.javaClass.name, it.message ?: "best feed error") }
        }
    }

    fun getWeeklyHipPlaceAllData() {
        viewModelScope.launch {
            placeRepository.getPlaceHipSearchAllData(UserData.userId)
                .onSuccess { _weeklyHipPlaceAllData.value = it.data }
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

    // 서버 연결시 FAKE 데이터 삭제
    private val fakeBannerUrl =
        "https://user-images.githubusercontent.com/64943924/156609871-0f94812a-286d-4120-989c-91e8ddbb1086.png"
    private val fakeUrl = "https://source.unsplash.com/random"
    private val fakeLocalHipsterAllData: List<LocalHipsterData> = listOf(
        LocalHipsterData(1, Area.JEJU, "제주의 맛맛맛", "제주 해녀의 부엌 외 5곳", fakeUrl),
        LocalHipsterData(2, Area.JEJU, "제주의 맛맛맛", "제주 해녀의 부엌 외 5곳", fakeUrl),
        LocalHipsterData(3, Area.JEJU, "제주의 맛맛맛", "제주 해녀의 부엌 외 5곳", fakeUrl),
        LocalHipsterData(4, Area.JEJU, "제주의 맛맛맛", "제주 해녀의 부엌 외 5곳", fakeUrl),
        LocalHipsterData(5, Area.JEJU, "제주의 맛맛맛", "제주 해녀의 부엌 외 5곳", fakeUrl),
    )
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
