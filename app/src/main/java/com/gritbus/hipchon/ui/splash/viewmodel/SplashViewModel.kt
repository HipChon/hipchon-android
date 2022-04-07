package com.gritbus.hipchon.ui.splash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.UserData
import com.gritbus.hipchon.data.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _canLoginAuto = MutableLiveData<Boolean>()
    val canLoginAuto: LiveData<Boolean> = _canLoginAuto

    private val _isLoginSuccess = MutableLiveData<Boolean>()
    val isLoginSuccess: LiveData<Boolean> = _isLoginSuccess

    private val _hasUserId = MutableLiveData<Boolean>()
    val hasUserId: LiveData<Boolean> = _hasUserId

    fun getAutoInfo() {
        val loginId = userRepository.getAutoLoginId()?.run {
            UserData.userLoginId = this
        }
        val platform = userRepository.getAutoLoginPlatform()?.run {
            UserData.platform = this
        }

        _canLoginAuto.value = loginId != null && platform != null
    }

    fun loginAuto() {
        viewModelScope.launch {
            userRepository.loginUser(UserData.platform, UserData.userLoginId)
                .onSuccess {
                    _isLoginSuccess.value = true
                }
                .onFailure {
                    _isLoginSuccess.value = false
                }
        }
    }

    fun getUserId() {
        viewModelScope.launch {
            userRepository.getUserData(UserData.platform, UserData.userLoginId)
                .onSuccess {
                    UserData.userId = it.userId
                    _hasUserId.value = true
                }
                .onFailure {
                    _hasUserId.value = false
                }
        }
    }
}
