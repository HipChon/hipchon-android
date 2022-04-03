package com.gritbus.hipchon.ui.save.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.UserData
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
) : ViewModel() {

    private lateinit var category: String
    private val categoryMap: Map<String, Int> = mapOf("카페" to 1, "미식" to 2, "활동" to 3, "자연" to 4)

    private val _savePlaceAllData = MutableLiveData<List<MyPlaceAllDataItem>>()
    val savePlaceAllData: LiveData<List<MyPlaceAllDataItem>> = _savePlaceAllData

    init {
        savedStateHandle.get<String>(CATEGORY_TITLE)?.let {
            category = it
        }
    }

    fun getMySavePlace() {
        viewModelScope.launch {
            when (category) {
                "전체" -> {
                    myRepository.getMyPlace(UserData.userId)
                        .onSuccess {
                            _savePlaceAllData.value = it
                        }
                        .onFailure {
                            Log.e(this.javaClass.name, it.message ?: "my place error")
                        }
                }
                else -> {
                    categoryMap[category]?.let {
                        myRepository.getMyPlaceWithCategory(UserData.userId, it)
                            .onSuccess { placeData ->
                                _savePlaceAllData.value = placeData
                            }
                            .onFailure { throwable ->
                                Log.e(this.javaClass.name, throwable.message ?: "my place error")
                            }
                    }
                }
            }
        }
    }
}
