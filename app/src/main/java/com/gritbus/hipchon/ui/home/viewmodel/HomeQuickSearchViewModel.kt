package com.gritbus.hipchon.ui.home.viewmodel

import com.gritbus.hipchon.domain.model.Area
import com.gritbus.hipchon.domain.model.PlaceSearchFilterData
import com.gritbus.hipchon.domain.model.Type
import com.gritbus.hipchon.utils.QuickSearchViewModel

class HomeQuickSearchViewModel : QuickSearchViewModel() {

    init {
        initialFilterData = PlaceSearchFilterData(Area.ALL, Type.NOTHING)
    }
}
