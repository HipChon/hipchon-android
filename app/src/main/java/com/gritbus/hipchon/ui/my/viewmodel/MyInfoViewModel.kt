package com.gritbus.hipchon.ui.my.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.UserData
import com.gritbus.hipchon.data.model.user.UserInfoData
import com.gritbus.hipchon.data.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyInfoViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _userProfile = MutableLiveData<UserInfoData>()
    val userProfile: LiveData<UserInfoData> = _userProfile

    fun getUserProfile() {
        viewModelScope.launch {
            userRepository.getUserData(UserData.platform, UserData.userLoginId)
                .onSuccess {
                    _userProfile.value = it
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "user profile error")
                }
        }
    }
}