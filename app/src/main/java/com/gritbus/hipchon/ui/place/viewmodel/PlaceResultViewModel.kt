package com.gritbus.hipchon.ui.place.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.gritbus.hipchon.domain.model.PlaceData
import com.gritbus.hipchon.domain.model.PlaceOrderType
import com.gritbus.hipchon.domain.model.PlaceSearchFilterData
import com.gritbus.hipchon.ui.home.view.HomeQuickSearchFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaceResultViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _placeSearchFilterData = MutableLiveData<PlaceSearchFilterData>()
    val placeSearchFilterData: LiveData<PlaceSearchFilterData> = _placeSearchFilterData

    private val _placeAllData = MutableLiveData<List<PlaceData>>()
    val placeAllData: LiveData<List<PlaceData>> = _placeAllData

    private val _placeOrderType = MutableLiveData(PlaceOrderType.FEED)
    val placeOrderType: LiveData<PlaceOrderType> = _placeOrderType

    init {
        _placeSearchFilterData.value =
            savedStateHandle.get<PlaceSearchFilterData>(HomeQuickSearchFragment.QUICK_SEARCH_FILTER)
    }

    fun updateFilterData(filterData: PlaceSearchFilterData) {
        _placeSearchFilterData.value = filterData
    }

    fun setOrderType(orderType: PlaceOrderType) {
        _placeOrderType.value = orderType
        getPlaceData()
    }

    fun updateSave(selectedPlaceData: PlaceData) {
        fakeDataSet = fakeDataSet.map {
            if (it.id == selectedPlaceData.id) {
                it.copy(isSave = !it.isSave)
            } else {
                it
            }
        }
        getPlaceData()
    }

    fun getPlaceData() {
        val orderType = _placeOrderType.value ?: return

        // TODO 서버와 연결 필요
        _placeAllData.value = when (orderType) {
            PlaceOrderType.SAVE -> fakeDataSet.sortedByDescending { it.saveCount }
            PlaceOrderType.FEED -> fakeDataSet.sortedByDescending { it.feedCount }
            PlaceOrderType.DISTANCE -> fakeDataSet.sortedByDescending { it.distance }
        }
    }

    fun getSearchFilter(): PlaceSearchFilterData? {
        return _placeSearchFilterData.value
    }

    // 서버 연결시 삭제할 부분
    private val fakeUrl = "https://source.unsplash.com/random"
    private var fakeDataSet: List<PlaceData> = listOf(
        PlaceData(
            1,
            listOf(fakeUrl, fakeUrl, fakeUrl, fakeUrl, fakeUrl, fakeUrl),
            false,
            "[경기] 더반올가닉",
            1,
            "3인가능",
            "논밭뷰",
            "뷰맛집",
            30,
            30,
            "1,000,000원부터"
        ), PlaceData(
            2,
            listOf(fakeUrl, fakeUrl, fakeUrl, fakeUrl, fakeUrl, fakeUrl),
            false,
            "[경기] 더반올가닉",
            2,
            "3인가능",
            "논밭뷰",
            "뷰맛집",
            40,
            9,
            "800,000원부터"
        ), PlaceData(
            3,
            listOf(fakeUrl, fakeUrl, fakeUrl, fakeUrl, fakeUrl, fakeUrl),
            false,
            "[경기] 더반올가닉",
            2,
            "3인가능",
            "논밭뷰",
            "뷰맛집",
            50,
            23,
            "800,000원부터"
        ), PlaceData(
            4,
            listOf(fakeUrl, fakeUrl, fakeUrl, fakeUrl, fakeUrl, fakeUrl),
            false,
            "[경기] 더반올가닉",
            4,
            "3인가능",
            "논밭뷰",
            "뷰맛집",
            13,
            10,
            "1,000,000원부터"
        ), PlaceData(
            5,
            listOf(fakeUrl, fakeUrl, fakeUrl, fakeUrl, fakeUrl, fakeUrl),
            false,
            "[경기] 더반올가닉",
            3,
            "3인가능",
            "논밭뷰",
            "뷰맛집",
            253,
            142,
            "1,000,000원부터"
        ), PlaceData(
            6,
            listOf(fakeUrl, fakeUrl, fakeUrl, fakeUrl, fakeUrl, fakeUrl),
            false,
            "[경기] 더반올가닉",
            5,
            "3인가능",
            "논밭뷰",
            "뷰맛집",
            1,
            0,
            "1,000,000원부터"
        )
    )
}
