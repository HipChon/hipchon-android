package com.gritbus.hipchon.ui.home.viewmodel

import com.gritbus.hipchon.domain.model.Area
import com.gritbus.hipchon.domain.model.Hashtag
import com.gritbus.hipchon.domain.model.PlaceSearchFilterData
import com.gritbus.hipchon.utils.QuickSearchViewModel

class HomeQuickSearchViewModel : QuickSearchViewModel() {

    init {
        initialFilterData = PlaceSearchFilterData(1, false, Area.ALL, Hashtag.NOTHING)
    }
}
