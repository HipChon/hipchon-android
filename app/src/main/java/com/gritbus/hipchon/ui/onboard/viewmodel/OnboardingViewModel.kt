package com.gritbus.hipchon.ui.onboard.viewmodel

import android.util.Log
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
class OnboardingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isLoginSuccess = MutableLiveData<Boolean>()
    val isLoginSuccess: LiveData<Boolean> = _isLoginSuccess

    private val _hipchoniCount = MutableLiveData(0)
    val hipchoniCount: LiveData<Int> = _hipchoniCount

    fun userLogin(id: String, platform: String) {
        UserData.platform = platform
        UserData.userLoginId = id
        Log.i("user", UserData.userLoginId)

        viewModelScope.launch {
            userRepository.loginUser(platform, id)
                .onSuccess {
                    userRepository.getUserData(platform, id)
                        .onSuccess { userData ->
                            UserData.userId = userData.userId
                            userRepository.setAutoLoginId(UserData.userLoginId)
                            userRepository.setAutoLoginPlatform(UserData.platform)
                            _isLoginSuccess.value = true
                        }
                        .onFailure {
                            Log.e(this.javaClass.name, it.message ?: "user data error")
                            _isLoginSuccess.value = false
                        }
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "login error")
                    _isLoginSuccess.value = false
                }
        }
    }

    fun updateHipchoniCount() {
        _hipchoniCount.value = _hipchoniCount.value?.plus(1)
    }

    fun loginWithMasterAccount() {
        UserData.platform = "naver"
        UserData.userLoginId = "masterId"
        viewModelScope.launch {
            userRepository.loginUser(UserData.platform, UserData.userLoginId)
                .onSuccess {
                    userRepository.getUserData(UserData.platform, UserData.userLoginId)
                        .onSuccess { userData ->
                            UserData.userId = userData.userId
                            userRepository.setAutoLoginId(UserData.userLoginId)
                            userRepository.setAutoLoginPlatform(UserData.platform)
                            _isLoginSuccess.value = true
                        }
                        .onFailure {
                            Log.e(this.javaClass.name, it.message ?: "user data error")
                            _isLoginSuccess.value = false
                        }
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "login error")
                    _isLoginSuccess.value = false
                }
        }
    }
}
