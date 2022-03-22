package com.gritbus.hipchon.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gritbus.hipchon.domain.model.Area
import com.gritbus.hipchon.domain.model.Hashtag
import com.gritbus.hipchon.domain.model.PlaceSearchFilterData

abstract class QuickSearchViewModel : ViewModel() {

    lateinit var initialFilterData: PlaceSearchFilterData

    private val _area = MutableLiveData(Area.ALL)
    val area: LiveData<Area> = _area

    private val _hashtag = MutableLiveData(Hashtag.NOTHING)
    val hashtag: LiveData<Hashtag> = _hashtag

    private val _isFilterChange = MutableLiveData(false)
    val isFilterChange: LiveData<Boolean> = _isFilterChange

    fun initData() {
        resetAllFilter()
    }

    fun setArea(checkedId: Int?) {
        _area.value = getAreaWithId(checkedId)
        checkFilterChange()
    }

    fun setHashtag(checkedId: Int?) {
        _hashtag.value = getHashtagWithId(checkedId)
        checkFilterChange()
    }

    fun resetAllFilter() {
        _area.value = initialFilterData.area
        _hashtag.value = initialFilterData.hashtag
        checkFilterChange()
    }

    private fun checkFilterChange() {
        _isFilterChange.value = _area.value != initialFilterData.area ||
                _hashtag.value != initialFilterData.hashtag
    }

    fun getSearchFilter(): PlaceSearchFilterData? {
        val areaData = _area.value ?: return null
        val hashtagData = _hashtag.value ?: return null

        return PlaceSearchFilterData(
            areaData,
            hashtagData
        )
    }
}
