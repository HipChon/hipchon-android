package com.gritbus.hipchon.ui.feed.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.UserData
import com.gritbus.hipchon.data.model.feed.FeedAllDataItem
import com.gritbus.hipchon.data.repository.feed.FeedRepository
import com.gritbus.hipchon.domain.model.FeedOrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : ViewModel() {

    private val _reviewAllData = MutableLiveData<List<FeedAllDataItem>>()
    val reviewAllData: LiveData<List<FeedAllDataItem>> = _reviewAllData

    private val _reviewOrderType = MutableLiveData(FeedOrderType.RECENT)
    val reviewOrderType: LiveData<FeedOrderType> = _reviewOrderType

    fun initData() {
        val orderType = _reviewOrderType.value?.let {
            if (it == FeedOrderType.LIKE) "like" else "recent"
        } ?: return

        viewModelScope.launch {
            feedRepository.getFeedAllData(UserData.userId, orderType)
                .onSuccess {
                    _reviewAllData.value = it.data
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "review error")
                }
        }
    }

    fun setOrderType(orderType: FeedOrderType) {
        _reviewOrderType.value = orderType
    }
}
