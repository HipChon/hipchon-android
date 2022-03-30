package com.gritbus.hipchon.ui.place.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.UserData
import com.gritbus.hipchon.data.model.feed.FeedAllDataItem
import com.gritbus.hipchon.data.model.place.KeywordItem
import com.gritbus.hipchon.data.model.place.PlaceDetailData
import com.gritbus.hipchon.data.repository.feed.FeedRepository
import com.gritbus.hipchon.data.repository.place.PlaceRepository
import com.gritbus.hipchon.ui.place.view.PlaceResultActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class PlaceDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val placeRepository: PlaceRepository,
    private val feedRepository: FeedRepository
) : ViewModel() {

    private var placeId by Delegates.notNull<Int>()

    private val _placeData = MutableLiveData<PlaceDetailData>()
    val placeData: LiveData<PlaceDetailData> = _placeData

    private val _thumbImages = MutableLiveData<List<String>>()
    val thumbImages: LiveData<List<String>> = _thumbImages

    private val _menuAllData = MutableLiveData<List<String>>()
    val menuAllData: LiveData<List<String>> = _menuAllData

    private val _isSave = MutableLiveData<Boolean>(false)
    val isSave: LiveData<Boolean> = _isSave

    private val _keyword = MutableLiveData<List<KeywordItem>>()
    val keyword: LiveData<List<KeywordItem>> = _keyword

    private val _reviewPreview = MutableLiveData<List<FeedAllDataItem>>()
    val reviewPreview: LiveData<List<FeedAllDataItem>> = _reviewPreview

    init {
        savedStateHandle.get<Int>(PlaceResultActivity.PLACE_ID)?.let {
            placeId = it
        }
    }
    fun initData() {
        viewModelScope.launch {
            placeRepository.getPlaceDetailData(UserData.userId, placeId)
                .onSuccess {
                    _thumbImages.value = it.imageList
                    _placeData.value = it
                    _keyword.value = it.keywordList
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "place detail error")
                }
            feedRepository.getFeedWithPlaceAllData(placeId)
                .onSuccess {
                    _reviewPreview.value = it
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "place review error")
                }
        }

        // TODO 메뉴, 키워드 정보 서버와 연동
        _menuAllData.value = emptyList()
    }

    fun setSave() {
        _isSave.value = _isSave.value?.let { !it }
    }

    fun getReviewPlaceId(): Int {
        return placeId
    }

    fun getContact(): String? {
        return _placeData.value?.contact
    }
}
