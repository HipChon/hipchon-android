package com.gritbus.hipchon.ui.place.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.UserData
import com.gritbus.hipchon.data.model.place.PlaceSearchAllDataItem
import com.gritbus.hipchon.data.repository.place.PlaceRepository
import com.gritbus.hipchon.domain.mapper.areaValueToId
import com.gritbus.hipchon.domain.mapper.categoryValueToId
import com.gritbus.hipchon.domain.mapper.hashtagValueToId
import com.gritbus.hipchon.domain.model.Hashtag
import com.gritbus.hipchon.domain.model.PlaceOrderType
import com.gritbus.hipchon.domain.model.PlaceSearchFilterData
import com.gritbus.hipchon.ui.home.view.HomeFragment.Companion.HASHTAG_SEARCH
import com.gritbus.hipchon.ui.home.view.HomeQuickSearchFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceResultViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val placeRepository: PlaceRepository
) : ViewModel() {

    // 검색 방법 : 일반 검색 / 해시태그 검색
    private lateinit var _searchType: String

    // 일반 검색 옵션
    private val _searchOptionNormal = MutableLiveData<PlaceSearchFilterData>()
    val searchOptionNormal: LiveData<PlaceSearchFilterData> = _searchOptionNormal

    // 해시태그 검색 옵션
    private val _searchOptionHashtag = MutableLiveData<Hashtag>()
    val searchOptionHashtag: LiveData<Hashtag> = _searchOptionHashtag

    // 검색 결과
    private val _placeAllData = MutableLiveData<List<PlaceSearchAllDataItem>>()
    val placeAllData: LiveData<List<PlaceSearchAllDataItem>> = _placeAllData

    // 정렬 기준
    private val _placeOrderType = MutableLiveData(PlaceOrderType.FEED)
    val placeOrderType: LiveData<PlaceOrderType> = _placeOrderType

    init {
        val searchWithAreaAndType =
            savedStateHandle.get<PlaceSearchFilterData>(HomeQuickSearchFragment.QUICK_SEARCH_FILTER)
        val searchWithHashtag =
            savedStateHandle.get<Hashtag>(HASHTAG_SEARCH)

        searchWithAreaAndType?.let {
            _searchType = SEARCH_OPTION_NORMAL
            _searchOptionNormal.value = it
        } ?: searchWithHashtag?.let {
            _searchType = SEARCH_OPTION_HASHTAG
            _searchOptionHashtag.value = it
        }
    }

    fun getPlaceData() {
        val orderType = _placeOrderType.value?.let {
            if (it == PlaceOrderType.SAVE) "myplace" else "post"
        } ?: return

        viewModelScope.launch {
            when (_searchType) {
                SEARCH_OPTION_NORMAL -> {
                    val searchOptionNormal = _searchOptionNormal.value ?: return@launch

                    placeRepository.getPlaceSearchAllData(
                        UserData.userId,
                        areaValueToId(searchOptionNormal.area.value),
                        categoryValueToId(searchOptionNormal.type.value),
                        orderType
                    ).onSuccess {
                        _placeAllData.value = it
                    }.onFailure {
                        Log.e(this.javaClass.name, it.message ?: "search normal error")
                    }
                }
                SEARCH_OPTION_HASHTAG -> {
                    val searchOptionHashtag = _searchOptionHashtag.value ?: return@launch

                    placeRepository.getPlaceSearchWithHashtag(
                        UserData.userId,
                        hashtagValueToId(searchOptionHashtag.value),
                        orderType
                    ).onSuccess {
                        _placeAllData.value = it
                    }.onFailure {
                        Log.e(this.javaClass.name, it.message ?: "search hashtag error")
                    }
                }
            }
        }
    }

    fun updateFilterData(filterData: PlaceSearchFilterData) {
        _searchOptionNormal.value = filterData
        getPlaceData()
    }

    fun setOrderType(orderType: PlaceOrderType) {
        _placeOrderType.value = orderType
        getPlaceData()
    }

    fun getSearchFilter(): PlaceSearchFilterData? {
        return _searchOptionNormal.value
    }

    companion object {
        const val SEARCH_OPTION_NORMAL = "지역유형검색"
        const val SEARCH_OPTION_HASHTAG = "해시태그검색"
    }
}
