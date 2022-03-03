package com.gritbus.hipchon.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gritbus.hipchon.domain.model.Area
import com.gritbus.hipchon.domain.model.Hashtag
import com.gritbus.hipchon.domain.model.LocalHipsterData
import com.gritbus.hipchon.domain.model.WeeklyHipPlaceData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _localHipsterAllData = MutableLiveData<List<LocalHipsterData>>()
    val localHipsterAllData: LiveData<List<LocalHipsterData>> = _localHipsterAllData

    private val _bannerAllData = MutableLiveData<List<String>>()
    val bannerAllData: LiveData<List<String>> = _bannerAllData

    private val _bestFeedAllData = MutableLiveData<List<Pair<String, Hashtag>>>()
    val bestFeedAllData: LiveData<List<Pair<String, Hashtag>>> = _bestFeedAllData

    private val _weeklyHipPlaceAllData = MutableLiveData<List<WeeklyHipPlaceData>>()
    val weeklyHipPlaceAllData: LiveData<List<WeeklyHipPlaceData>> = _weeklyHipPlaceAllData

    fun getLocalHipsterAllData() {
        _localHipsterAllData.value = fakeLocalHipsterAllData
    }

    fun getBannerAllData() {
        _bannerAllData.value = fakeBannerAllData
    }

    fun getBestFeedAllData() {
        _bestFeedAllData.value = fakeBestFeedAllData
    }

    fun getWeeklyHipPlaceAllData() {
        _weeklyHipPlaceAllData.value = fakeWeeklyHipPlaceAllData
    }

    fun updateSave(selectedPlaceData: WeeklyHipPlaceData) {
        fakeWeeklyHipPlaceAllData = fakeWeeklyHipPlaceAllData.map {
            if (it.id == selectedPlaceData.id) {
                it.copy(isSave = !it.isSave)
            } else {
                it
            }
        }
        getWeeklyHipPlaceAllData()
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
    private val fakeBestFeedAllData: List<Pair<String, Hashtag>> = listOf(
        "정선에서 역대급\n힐링 불멍 스팟을 찾다1" to Hashtag.FIRE,
        "정선에서 역대급\n힐링 물멍 스팟을 찾다2" to Hashtag.WATER,
        "정선에서 역대급\n힐링 논밭뷰 스팟을 찾다3" to Hashtag.FIELD,
        "정선에서 역대급\n힐링 촌캉스 스팟을 찾다4" to Hashtag.VACATION,
        "정선에서 역대급\n힐링 불멍 스팟을 찾다5" to Hashtag.FIRE
    )
    private var fakeWeeklyHipPlaceAllData: List<WeeklyHipPlaceData> = listOf(
        WeeklyHipPlaceData(1, "제주맛집", Area.JEJU, "3인가능", "3인가능", 10, 12, false, fakeUrl),
        WeeklyHipPlaceData(2, "제주맛집", Area.JEJU, "3인가능", "3인가능", 10, 12, false, fakeUrl),
        WeeklyHipPlaceData(3, "제주맛집", Area.JEJU, "3인가능", "3인가능", 10, 12, false, fakeUrl),
        WeeklyHipPlaceData(4, "제주맛집", Area.JEJU, "3인가능", "3인가능", 10, 12, false, fakeUrl),
        WeeklyHipPlaceData(5, "제주맛집", Area.JEJU, "3인가능", "3인가능", 10, 12, false, fakeUrl),
        WeeklyHipPlaceData(6, "제주맛집", Area.JEJU, "3인가능", "3인가능", 10, 12, false, fakeUrl),
    )
}
