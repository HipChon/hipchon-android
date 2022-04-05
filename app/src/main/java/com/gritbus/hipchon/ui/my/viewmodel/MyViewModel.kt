package com.gritbus.hipchon.ui.my.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.UserData
import com.gritbus.hipchon.data.model.feed.FeedAllDataItem
import com.gritbus.hipchon.data.model.my.MyCommentAllDataItem
import com.gritbus.hipchon.data.model.my.MyFeedAllDataItem
import com.gritbus.hipchon.data.model.user.UserInfoData
import com.gritbus.hipchon.data.repository.feed.CommentRepository
import com.gritbus.hipchon.data.repository.feed.FeedRepository
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
    private val myRepository: MyRepository,
    private val feedRepository: FeedRepository,
    private val commentRepository: CommentRepository
) : ViewModel() {

    private val _myProfile = MutableLiveData<UserInfoData>()
    val myProfile: LiveData<UserInfoData> = _myProfile

    private val _myFeedAllData = MutableLiveData<List<MyFeedAllDataItem>>()
    val myFeedAllData: LiveData<List<MyFeedAllDataItem>> = _myFeedAllData

    private val _myLikeFeedAllData = MutableLiveData<List<MyFeedAllDataItem>>()
    val myLikeFeedAllData: LiveData<List<MyFeedAllDataItem>> = _myLikeFeedAllData

    private val _myCommentAllData = MutableLiveData<List<MyCommentAllDataItem>>()
    val myCommentAllData: LiveData<List<MyCommentAllDataItem>> = _myCommentAllData

    private val _postDetailData = MutableLiveData<FeedAllDataItem?>()
    val postDetailData: LiveData<FeedAllDataItem?> = _postDetailData

    private val _postDeleteSuccess = MutableLiveData<Boolean?>()
    val postDeleteSuccess: LiveData<Boolean?> = _postDeleteSuccess

    private val _postLikeDeleteSuccess = MutableLiveData<Boolean?>()
    val postLikeDeleteSuccess: LiveData<Boolean?> = _postLikeDeleteSuccess

    private val _commentDeleteSuccess = MutableLiveData<Boolean?>()
    val commentDeleteSuccess: LiveData<Boolean?> = _commentDeleteSuccess

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
                    _myLikeFeedAllData.value = it
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

    fun getPostDetail(postId: Int) {
        viewModelScope.launch {
            feedRepository.getFeedDetailData(UserData.userId, postId)
                .onSuccess {
                    _postDetailData.value = it
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "my comment error")
                }
        }
    }

    fun resetPostDetail() {
        _postDetailData.value = null
    }

    fun deleteComment(commentId: Int) {
        _commentDeleteSuccess.value = null
        viewModelScope.launch {
            commentRepository.deleteComment(commentId)
                .onSuccess {
                    _commentDeleteSuccess.value = true
                    getMyCommentData()
                }
                .onFailure {
                    _commentDeleteSuccess.value = false
                    Log.e(this.javaClass.name, it.message ?: "my comment error")
                }
        }
    }

    fun resetDeleteCommentStatus() {
        _commentDeleteSuccess.value = null
    }

    fun deletePostLike(postId: Int) {
        viewModelScope.launch {
            feedRepository.deletePostLike(UserData.userId, postId)
                .onSuccess {
                    _postLikeDeleteSuccess.value = true
                    getMyCommentData()
                }
                .onFailure {
                    _postLikeDeleteSuccess.value = false
                    Log.e(this.javaClass.name, it.message ?: "post like delete error")
                }
        }
    }

    fun resetDeletePostLikeStatus() {
        _postLikeDeleteSuccess.value = null
    }

    fun deletePost(postId: Int) {
        viewModelScope.launch {
            feedRepository.deletePost(UserData.userId, postId)
                .onSuccess {
                    _postDeleteSuccess.value = true
                    getMyCommentData()
                }
                .onFailure {
                    _postDeleteSuccess.value = false
                    Log.e(this.javaClass.name, it.message ?: "post delete error")
                }
        }
    }

    fun resetDeletePostStatus() {
        _postDeleteSuccess.value = null
    }
}
