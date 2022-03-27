package com.gritbus.hipchon.ui.my.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.my.MyFeedAllDataItem
import com.gritbus.hipchon.data.repository.my.MyRepository
import com.gritbus.hipchon.ui.my.view.MyFragment.Companion.MY_COMMENT
import com.gritbus.hipchon.ui.my.view.MyFragment.Companion.MY_FEED
import com.gritbus.hipchon.ui.my.view.MyFragment.Companion.MY_LIKE_FEED
import com.gritbus.hipchon.ui.my.view.MyReviewFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val myRepository: MyRepository
) : ViewModel() {

    private val _dataType = MutableLiveData<String>()
    val dataType: LiveData<String> = _dataType

    private val _myFeedAllData = MutableLiveData<List<MyFeedAllDataItem>>()
    val myFeedAllData: LiveData<List<MyFeedAllDataItem>> = _myFeedAllData

    private val _myCommentAllData = MutableLiveData<List<Int>>()
    val myCommentAllData: LiveData<List<Int>> = _myCommentAllData

    init {
        savedStateHandle.get<String>(MyReviewFragment.MY_REVIEW_FRAGMENT_TYPE)?.let {
            _dataType.value = it
        }
    }

    fun getMyData() {
        val dataType = _dataType.value ?: return
        when (dataType) {
            MY_FEED -> {
                getMyFeedData()
            }
            MY_LIKE_FEED -> {
                getMyLikeFeedData()
            }
            MY_COMMENT -> {
                getMyCommentData()
            }
        }
    }

    fun getMyFeedData() {
        viewModelScope.launch {
            myRepository.getMyFeedAllData(5)
                .onSuccess {
                    _myFeedAllData.value = it.data
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "my feed error")
                }
        }
    }

    fun getMyLikeFeedData() {
        viewModelScope.launch {
            myRepository.getMyLikeFeedAllData(5)
                .onSuccess {
                    _myFeedAllData.value = it.data
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "my like feed error")
                }
        }
    }

    fun getMyCommentData() {
        _myCommentAllData.value = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    }
}
