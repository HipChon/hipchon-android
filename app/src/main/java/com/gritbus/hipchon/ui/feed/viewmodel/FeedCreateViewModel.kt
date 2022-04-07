package com.gritbus.hipchon.ui.feed.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.UserData
import com.gritbus.hipchon.data.model.feed.FeedCreateData
import com.gritbus.hipchon.data.model.place.PlaceDetailData
import com.gritbus.hipchon.data.repository.feed.FeedRepository
import com.gritbus.hipchon.ui.place.view.PlaceDetailActivity.Companion.FEED_POST_INFO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedCreateViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val feedRepository: FeedRepository
) : ViewModel() {

    private val _placeData = MutableLiveData<PlaceDetailData>()
    val placeData: LiveData<PlaceDetailData> = _placeData

    private var keywordList = listOf<Int>()

    private val _sendSuccess = MutableLiveData<Boolean>()
    val sendSuccess: LiveData<Boolean> = _sendSuccess

    init {
        savedStateHandle.get<PlaceDetailData>(FEED_POST_INFO)?.let {
            _placeData.value = it
        }
    }

    fun updateCheckedKeywordList(checkedKeywordList: List<Int>) {
        keywordList = checkedKeywordList
    }

    fun getCheckedKeywordList() = keywordList

    fun sendPost(detail: String, fileUriList: MutableList<Uri>) {
        val placeId = _placeData.value?.placeId ?: return

        viewModelScope.launch {
            feedRepository.sendPost(
                fileUriList,
                FeedCreateData(detail, keywordList, placeId, UserData.userId)
            ).onSuccess {
                _sendSuccess.value = true
            }.onFailure {
                Log.e(this.javaClass.name, it.message ?: "send post error")
            }
        }
    }
}

