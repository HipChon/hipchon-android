package com.gritbus.hipchon.ui.save.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.my.MyPlaceAllDataItem
import com.gritbus.hipchon.data.repository.my.MyRepository
import com.gritbus.hipchon.ui.save.view.SavePlaceFragment.Companion.CATEGORY_TITLE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val myRepository: MyRepository
): ViewModel() {

    private lateinit var category: String

    private val _savePlaceAllData = MutableLiveData<List<MyPlaceAllDataItem>>()
    val savePlaceAllData: LiveData<List<MyPlaceAllDataItem>> = _savePlaceAllData

    init {
        savedStateHandle.get<String>(CATEGORY_TITLE)?.let {
            category = it
        }
    }

    fun getMySavePlace(){
        viewModelScope.launch {
            myRepository.getMyPlace(5)
                .onSuccess { placeData ->
                    _savePlaceAllData.value = when(category){
                        "전체" -> placeData.data
                        else -> placeData.data.filter { it.category == category }
                    }
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "my place error")
                }
        }
    }
}
