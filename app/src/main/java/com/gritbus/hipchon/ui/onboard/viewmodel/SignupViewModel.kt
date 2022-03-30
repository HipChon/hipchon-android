package com.gritbus.hipchon.ui.onboard.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.UserData
import com.gritbus.hipchon.data.model.user.UserInfoData
import com.gritbus.hipchon.data.repository.user.UserRepository
import com.gritbus.hipchon.ui.onboard.view.SignupTermsFragment.Companion.TERM_AGE
import com.gritbus.hipchon.ui.onboard.view.SignupTermsFragment.Companion.TERM_ALL
import com.gritbus.hipchon.ui.onboard.view.SignupTermsFragment.Companion.TERM_EVENT
import com.gritbus.hipchon.ui.onboard.view.SignupTermsFragment.Companion.TERM_PERSONAL
import com.gritbus.hipchon.ui.onboard.view.SignupTermsFragment.Companion.TERM_POSITION
import com.gritbus.hipchon.ui.onboard.view.SignupTermsFragment.Companion.TERM_SERVICE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isMovable = MutableLiveData(false)
    val isMovable: LiveData<Boolean> = _isMovable

    private val _allTerms = MutableLiveData(false)
    val allTerms: LiveData<Boolean> = _allTerms

    private val _ageTerms = MutableLiveData(false)
    val ageTerms: LiveData<Boolean> = _ageTerms

    private val _serviceTerms = MutableLiveData(false)
    val serviceTerms: LiveData<Boolean> = _serviceTerms

    private val _personalTerms = MutableLiveData(false)
    val personalTerms: LiveData<Boolean> = _personalTerms

    private val _positionTerms = MutableLiveData(false)
    val positionTerms: LiveData<Boolean> = _positionTerms

    private val _eventTerms = MutableLiveData(false)
    val eventTerms: LiveData<Boolean> = _eventTerms

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> = _nickname

    private val _profilePath = MutableLiveData<Uri>()
    val profilePath: LiveData<Uri> = _profilePath

    private val _isSignupSuccess = MutableLiveData<Boolean>()
    val isSignupSuccess: LiveData<Boolean> = _isSignupSuccess

    fun updateTermsStatus(termType: String) {
        when (termType) {
            TERM_ALL -> {
                updateAllTermValue()
            }
            TERM_AGE -> {
                updateTermValue(_ageTerms)
            }
            TERM_SERVICE -> {
                updateTermValue(_serviceTerms)
            }
            TERM_PERSONAL -> {
                updateTermValue(_personalTerms)
            }
            TERM_POSITION -> {
                updateTermValue(_positionTerms)
            }
            TERM_EVENT -> {
                updateTermValue(_eventTerms)
            }
        }
    }

    private fun updateAllTermValue() {
        val value = _allTerms.value != true

        _ageTerms.value = value
        _serviceTerms.value = value
        _personalTerms.value = value
        _positionTerms.value = value
        _eventTerms.value = value

        _allTerms.value = value
        _isMovable.value = value
    }

    private fun updateTermValue(termValue: MutableLiveData<Boolean>) {
        termValue.value?.let {
            termValue.value = !it
        }

        _allTerms.value = _ageTerms.value == true && _serviceTerms.value == true
                && _personalTerms.value == true && _positionTerms.value == true
                && _eventTerms.value == true

        _isMovable.value = _ageTerms.value == true && _serviceTerms.value == true
                && _personalTerms.value == true && _positionTerms.value == true
    }

    fun setUserEmail(email: String) {
        _email.value = email
    }

    fun setUserNickname(nickname: String) {
        _nickname.value = nickname
    }

    fun setUserProfilePath(profilePath: Uri) {
        _profilePath.value = profilePath
    }

    fun userSignup() {
        val userDto = UserInfoData(
            email = _email.value ?: "",
            id = 0,
            isMarketing = _eventTerms.value == true,
            loginId = UserData.userLoginId,
            loginType = UserData.platform,
            name = _nickname.value ?: return,
            profileImage = ""
        )

        viewModelScope.launch {
            userRepository.signupUser(userDto)
                .onSuccess {
                    _isSignupSuccess.value = true
                }
                .onFailure {
                    _isSignupSuccess.value = false
                    Log.e(this.javaClass.name, it.message ?: "signup error")
                }
        }
    }
}
