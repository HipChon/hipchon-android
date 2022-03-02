package com.gritbus.hipchon.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gritbus.hipchon.domain.model.Area
import com.gritbus.hipchon.domain.model.LocalHipsterData
import com.gritbus.hipchon.domain.model.WeeklyHipPlaceData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _localHipsterAllData = MutableLiveData<List<LocalHipsterData>>()
    val localHipsterAllData: LiveData<List<LocalHipsterData>> = _localHipsterAllData

    private val _weeklyHipPlaceAllData = MutableLiveData<List<WeeklyHipPlaceData>>()
    val weeklyHipPlaceAllData: LiveData<List<WeeklyHipPlaceData>> = _weeklyHipPlaceAllData

    fun getLocalHipsterAllData() {
        _localHipsterAllData.value = fakeLocalHipsterAllData
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
    private val fakeUrl = "https://source.unsplash.com/random"
    private val fakeLocalHipsterAllData: List<LocalHipsterData> = listOf(
        LocalHipsterData(1, Area.JEJU, "제주의 맛맛맛", "제주 해녀의 부엌 외 5곳", fakeUrl),
        LocalHipsterData(2, Area.JEJU, "제주의 맛맛맛", "제주 해녀의 부엌 외 5곳", fakeUrl),
        LocalHipsterData(3, Area.JEJU, "제주의 맛맛맛", "제주 해녀의 부엌 외 5곳", fakeUrl),
        LocalHipsterData(4, Area.JEJU, "제주의 맛맛맛", "제주 해녀의 부엌 외 5곳", fakeUrl),
        LocalHipsterData(5, Area.JEJU, "제주의 맛맛맛", "제주 해녀의 부엌 외 5곳", fakeUrl),
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
