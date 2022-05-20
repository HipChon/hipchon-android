package com.gritbus.hipchon.ui.my.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.UserData
import com.gritbus.hipchon.data.repository.feed.CommentRepository
import com.gritbus.hipchon.data.repository.feed.FeedRepository
import com.gritbus.hipchon.data.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MySettingViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository,
    private val feedRepository: FeedRepository
) : ViewModel() {

    private val _isLogoutSuccess = MutableLiveData<Boolean>()
    val isLogoutSuccess: LiveData<Boolean> = _isLogoutSuccess

    private val _isLeaveSuccess = MutableLiveData<Boolean>()
    val isLeaveSuccess: LiveData<Boolean> = _isLeaveSuccess

    fun logoutUser() {
        userRepository.setAutoLoginId(null)
        userRepository.setAutoLoginPlatform(null)
        userRepository.setUserReportAllData(null)
        feedRepository.setFeedReportAllData(null)
        commentRepository.setCommentReportAllData(null)

        _isLogoutSuccess.value =
            userRepository.getAutoLoginId() == null && userRepository.getAutoLoginPlatform() == null
    }

    fun leaveUser() {
        if (UserData.userLoginId == "masterId") {
            userRepository.setAutoLoginId(null)
            userRepository.setAutoLoginPlatform(null)
            userRepository.setUserReportAllData(null)
            feedRepository.setFeedReportAllData(null)
            commentRepository.setCommentReportAllData(null)

            _isLeaveSuccess.value =
                userRepository.getAutoLoginId() == null && userRepository.getAutoLoginPlatform() == null
            return
        }
        viewModelScope.launch {
            userRepository.deleteUserData(UserData.platform, UserData.userLoginId)
                .onSuccess {
                    userRepository.setAutoLoginId(null)
                    userRepository.setAutoLoginPlatform(null)
                    userRepository.setUserReportAllData(null)
                    feedRepository.setFeedReportAllData(null)
                    commentRepository.setCommentReportAllData(null)

                    _isLeaveSuccess.value =
                        userRepository.getAutoLoginId() == null && userRepository.getAutoLoginPlatform() == null
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "delete user error")
                }
        }
    }
}
