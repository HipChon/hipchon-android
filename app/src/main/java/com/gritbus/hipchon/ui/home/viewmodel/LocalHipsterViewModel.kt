package com.gritbus.hipchon.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.UserData
import com.gritbus.hipchon.data.model.place.LocalHipsterDetailData
import com.gritbus.hipchon.data.model.place.LocalHipsterPlace
import com.gritbus.hipchon.data.repository.place.PlaceRepository
import com.gritbus.hipchon.ui.home.view.HomeFragment.Companion.LOCAL_HIPSTER_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalHipsterViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val placeRepository: PlaceRepository
) : ViewModel() {

    private val hipsterId = savedStateHandle.get<Int>(LOCAL_HIPSTER_ID)

    private val _localHipsterData = MutableLiveData<LocalHipsterDetailData>()
    val localHipsterData: LiveData<LocalHipsterDetailData> = _localHipsterData

    fun getLocalHipsterData() {
        viewModelScope.launch {
            hipsterId?.let { id ->
                placeRepository.getLocalHipsterDetailData(UserData.userId, id)
                    .onSuccess {
                        _localHipsterData.value = it
                    }
                    .onFailure {
                        Log.e(this.javaClass.name, it.message ?: "local hipster error")
                    }
            }
        }
    }

    fun savePlace(place: LocalHipsterPlace) {
        val isCurrentSaveState = place.isMyplace
        viewModelScope.launch {
            when (isCurrentSaveState) {
                true -> {
                    placeRepository.deletePlace(UserData.userId, place.placeId)
                        .onSuccess {
                            getLocalHipsterData()
                        }
                        .onFailure {
                            Log.e(this.javaClass.name, it.message ?: "hip place delete error")
                        }
                }
                false -> {
                    placeRepository.savePlace(UserData.userId, place.placeId)
                        .onSuccess {
                            getLocalHipsterData()
                        }
                        .onFailure {
                            Log.e(this.javaClass.name, it.message ?: "hip place save error")
                        }
                }
            }
        }
    }
}
