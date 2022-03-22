package com.gritbus.hipchon.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gritbus.hipchon.domain.model.Area
import com.gritbus.hipchon.domain.model.PlaceSearchFilterData
import com.gritbus.hipchon.domain.model.Type

abstract class QuickSearchViewModel : ViewModel() {

    lateinit var initialFilterData: PlaceSearchFilterData

    private val _area = MutableLiveData(Area.ALL)
    val area: LiveData<Area> = _area

    private val _type = MutableLiveData(Type.NOTHING)
    val type: LiveData<Type> = _type

    private val _isFilterChange = MutableLiveData(false)
    val isFilterChange: LiveData<Boolean> = _isFilterChange

    fun initData() {
        resetAllFilter()
    }

    fun setArea(checkedArea: String) {
        _area.value = Area.values().find { it.value == checkedArea }
        checkFilterChange()
    }

    fun setType(checkedType: String) {
        _type.value = Type.values().find { it.value == checkedType }
        checkFilterChange()
    }

    fun resetAllFilter() {
        _area.value = initialFilterData.area
        _type.value = initialFilterData.type
        checkFilterChange()
    }

    private fun checkFilterChange() {
        _isFilterChange.value = _area.value != initialFilterData.area ||
                _type.value != initialFilterData.type
    }

    fun getSearchFilter(): PlaceSearchFilterData? {
        val areaData = _area.value ?: return null
        val typeData = _type.value ?: return null

        return PlaceSearchFilterData(
            areaData,
            typeData
        )
    }
}
