package com.gritbus.hipchon.ui.my.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.UserData
import com.gritbus.hipchon.data.model.user.UserDataForUpdate
import com.gritbus.hipchon.data.model.user.UserInfoData
import com.gritbus.hipchon.data.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyUpdateViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _userData = MutableLiveData<UserInfoData>()
    val userData: LiveData<UserInfoData> = _userData

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _userImage = MutableLiveData<Uri>()
    val userImage: LiveData<Uri> = _userImage

    private val _isSignupSuccess = MutableLiveData<String>()
    val isSignupSuccess: LiveData<String> = _isSignupSuccess

    fun getUserInfo() {
        viewModelScope.launch {
            userRepository.getUserData(UserData.platform, UserData.userLoginId)
                .onSuccess {
                    _userData.value = it
                    _userName.value = it.name
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "user profile error")
                }
        }
    }

    fun setUserNickname(name: String) {
        _userName.value = name
    }

    fun setUserProfile(uri: Uri) {
        _userImage.value = uri
    }

    fun userSignup() {
        val userDto = UserDataForUpdate(
            loginId = UserData.userLoginId,
            loginType = UserData.platform,
            name = _userName.value ?: return,
            email = _userData.value?.email ?: return,
            isMarketing = _userData.value?.isMarketing ?: return,
        )

        viewModelScope.launch {
            userRepository.updateProfile(_userImage.value, userDto)
                .onSuccess {
                    _isSignupSuccess.value = "success"
                }
                .onFailure {
                    _isSignupSuccess.value = it.message
                    Log.e(this.javaClass.name, it.message ?: "signup error")
                }
        }
    }
}
