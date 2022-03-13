package com.gritbus.hipchon.ui.feed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.domain.model.PlaceOrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor() : ViewModel() {

    private val _reviewAllData = MutableLiveData<List<Int>>()
    val reviewAllData: LiveData<List<Int>> = _reviewAllData

    private val _reviewOrderType = MutableLiveData(PlaceOrderType.FEED)
    val reviewOrderType: LiveData<PlaceOrderType> = _reviewOrderType

    fun initData() {
        viewModelScope.launch {
            _reviewAllData.value = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        }
    }

    fun setOrderType(orderType: PlaceOrderType) {
        _reviewOrderType.value = orderType
    }
}
