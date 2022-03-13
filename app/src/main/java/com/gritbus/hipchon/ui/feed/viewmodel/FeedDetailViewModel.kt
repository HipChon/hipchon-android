package com.gritbus.hipchon.ui.feed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedDetailViewModel @Inject constructor() : ViewModel() {

    private val _commentAllData = MutableLiveData<List<Int>>()
    val commentAllData: LiveData<List<Int>> = _commentAllData

    fun getCommentAll() {
        _commentAllData.value = listOf(1, 2, 3, 4, 5)
    }
}
