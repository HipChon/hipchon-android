package com.gritbus.hipchon.ui.place.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
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

    init {
        _placeSearchFilterData.value =
            savedStateHandle.get<PlaceSearchFilterData>(HomeQuickSearchFragment.QUICK_SEARCH_FILTER)
    }
}
