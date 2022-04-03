package com.gritbus.hipchon.ui.my.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.UserData
import com.gritbus.hipchon.data.model.my.MyCommentAllDataItem
import com.gritbus.hipchon.data.model.my.MyFeedAllDataItem
import com.gritbus.hipchon.data.model.user.UserInfoData
import com.gritbus.hipchon.data.repository.my.MyRepository
import com.gritbus.hipchon.data.repository.user.UserRepository
import com.gritbus.hipchon.ui.my.view.MyFragment.Companion.MY_COMMENT
import com.gritbus.hipchon.ui.my.view.MyFragment.Companion.MY_FEED
import com.gritbus.hipchon.ui.my.view.MyFragment.Companion.MY_LIKE_FEED
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val myRepository: MyRepository
) : ViewModel() {

    private val _myProfile = MutableLiveData<UserInfoData>()
    val myProfile: LiveData<UserInfoData> = _myProfile

    private val _myFeedAllData = MutableLiveData<List<MyFeedAllDataItem>>()
    val myFeedAllData: LiveData<List<MyFeedAllDataItem>> = _myFeedAllData

    private val _myCommentAllData = MutableLiveData<List<MyCommentAllDataItem>>()
    val myCommentAllData: LiveData<List<MyCommentAllDataItem>> = _myCommentAllData

    fun getMyData(type: String) {
        when (type) {
            MY_FEED -> {
                getMyFeedData()
            }
            MY_LIKE_FEED -> {
                getMyLikeFeedData()
            }
            MY_COMMENT -> {
                getMyCommentData()
            }
        }
    }

    fun getMyProfile() {
        viewModelScope.launch {
            userRepository.getUserData(UserData.platform, UserData.userLoginId)
                .onSuccess {
                    _myProfile.value = it
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "user profile error")
                }
        }
    }

    fun getMyFeedData() {
        viewModelScope.launch {
            myRepository.getMyFeedAllData(UserData.userId)
                .onSuccess {
                    _myFeedAllData.value = it
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "my feed error")
                }
        }
    }

    fun getMyLikeFeedData() {
        viewModelScope.launch {
            myRepository.getMyLikeFeedAllData(UserData.userId)
                .onSuccess {
                    _myFeedAllData.value = it
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "my like feed error")
                }
        }
    }

    fun getMyCommentData() {
        viewModelScope.launch {
            myRepository.getMyComment(UserData.userId)
                .onSuccess {
                    _myCommentAllData.value = it
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "my comment error")
                }
        }
    }
}
