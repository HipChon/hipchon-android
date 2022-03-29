package com.gritbus.hipchon.ui.onboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gritbus.hipchon.data.repository.user.UserRepository
import com.gritbus.hipchon.ui.onboard.view.SignupTermsFragment.Companion.TERM_AGE
import com.gritbus.hipchon.ui.onboard.view.SignupTermsFragment.Companion.TERM_ALL
import com.gritbus.hipchon.ui.onboard.view.SignupTermsFragment.Companion.TERM_EVENT
import com.gritbus.hipchon.ui.onboard.view.SignupTermsFragment.Companion.TERM_PERSONAL
import com.gritbus.hipchon.ui.onboard.view.SignupTermsFragment.Companion.TERM_POSITION
import com.gritbus.hipchon.ui.onboard.view.SignupTermsFragment.Companion.TERM_SERVICE
import dagger.hilt.android.lifecycle.HiltViewModel
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

        _isMovable.value =  _ageTerms.value == true && _serviceTerms.value == true
                && _personalTerms.value == true && _positionTerms.value == true
    }
}
