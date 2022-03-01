package com.gritbus.hipchon.ui.place.viewmodel

import com.gritbus.hipchon.domain.model.PlaceSearchFilterData
import com.gritbus.hipchon.utils.QuickSearchViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaceQuickSearchViewModel @Inject constructor(): QuickSearchViewModel() {

    fun initFilterData(filterData: PlaceSearchFilterData){
        initialFilterData = filterData
        initData()
    }
}
