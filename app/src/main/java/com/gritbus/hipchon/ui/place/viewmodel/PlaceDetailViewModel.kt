package com.gritbus.hipchon.ui.place.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaceDetailViewModel @Inject constructor() : ViewModel() {

    private val _thumbImages = MutableLiveData<List<String>>()
    val thumbImages: LiveData<List<String>> = _thumbImages

    private val _isSave = MutableLiveData<Boolean>(false)
    val isSave: LiveData<Boolean> = _isSave

    fun initData() {
        val fakeUrl = "https://source.unsplash.com/random"
        _thumbImages.value = listOf(fakeUrl, fakeUrl, fakeUrl, fakeUrl, fakeUrl)
    }

    fun setSave() {
        _isSave.value = _isSave.value?.let { !it }
    }
}
