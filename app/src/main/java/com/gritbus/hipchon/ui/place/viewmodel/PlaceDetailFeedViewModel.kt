package com.gritbus.hipchon.ui.place.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.feed.FeedAllDataItem
import com.gritbus.hipchon.data.repository.feed.FeedRepository
import com.gritbus.hipchon.ui.place.view.PlaceDetailActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class PlaceDetailFeedViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val feedRepository: FeedRepository
) : ViewModel() {

    private var _placeId by Delegates.notNull<Int>()

    private val _placeReviewAllData = MutableLiveData<List<FeedAllDataItem>>()
    val placeReviewAllData: LiveData<List<FeedAllDataItem>> = _placeReviewAllData

    init {
        savedStateHandle.get<Int>(PlaceDetailActivity.PLACE_DETAIL_FEED_MORE)?.let {
            _placeId = it
        }
    }

    fun getReviewData() {
        viewModelScope.launch {
            feedRepository.getFeedWithPlaceAllData(_placeId)
                .onSuccess {
                    _placeReviewAllData.value = it.data
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "place review more error")
                }
        }
    }
}