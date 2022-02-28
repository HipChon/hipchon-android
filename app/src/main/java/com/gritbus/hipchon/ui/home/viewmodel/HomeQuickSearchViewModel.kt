package com.gritbus.hipchon.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gritbus.hipchon.domain.model.Area
import com.gritbus.hipchon.domain.model.Hashtag
import com.gritbus.hipchon.domain.model.PlaceSearchFilterData
import com.gritbus.hipchon.utils.getAreaWithId
import com.gritbus.hipchon.utils.getHashtagWithId

class HomeQuickSearchViewModel : ViewModel() {

    private val _personCount = MutableLiveData(1)
    val personCount: LiveData<Int> = _personCount

    private val _withAnimal = MutableLiveData(false)
    val withAnimal: LiveData<Boolean> = _withAnimal

    private val _area = MutableLiveData(Area.ALL)
    val area: LiveData<Area> = _area

    private val _hashtag = MutableLiveData(Hashtag.NOTHING)
    val hashtag: LiveData<Hashtag> = _hashtag

    private val _isFilterChange = MutableLiveData(false)
    val isFilterChange: LiveData<Boolean> = _isFilterChange

    fun minusPersonCount() {
        _personCount.value = _personCount.value?.let {
            if (it > 1) {
                it.minus(1)
            } else {
                it
            }
        }
        checkFilterChange()
    }

    fun plusPersonCount() {
        _personCount.value = _personCount.value?.let {
            if (it < 9) {
                it.plus(1)
            } else {
                it
            }
        }
        checkFilterChange()
    }

    fun setWithAnimal(isChecked: Boolean) {
        _withAnimal.value = isChecked
        checkFilterChange()
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
        _personCount.value = 1
        _withAnimal.value = false
        _area.value = Area.ALL
        _hashtag.value = Hashtag.NOTHING
        checkFilterChange()
    }

    private fun checkFilterChange() {
        _isFilterChange.value = _personCount.value != 1 ||
                _withAnimal.value != false ||
                _area.value != Area.ALL ||
                _hashtag.value != Hashtag.NOTHING
    }

    fun getSearchFilter(): PlaceSearchFilterData? {
        val personCountData = _personCount.value ?: return null
        val withAnimalData = _withAnimal.value ?: return null
        val areaData = _area.value ?: return null
        val hashtagData = _hashtag.value ?: return null

        return PlaceSearchFilterData(
            personCountData,
            withAnimalData,
            areaData,
            hashtagData
        )
    }
}
