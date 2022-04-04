package com.gritbus.hipchon.ui.feed.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gritbus.hipchon.data.model.UserData
import com.gritbus.hipchon.data.model.feed.CommentAllDataItem
import com.gritbus.hipchon.data.model.feed.CommentData
import com.gritbus.hipchon.data.model.feed.FeedAllDataItem
import com.gritbus.hipchon.data.model.user.UserInfoData
import com.gritbus.hipchon.data.repository.feed.CommentRepository
import com.gritbus.hipchon.data.repository.feed.FeedRepository
import com.gritbus.hipchon.data.repository.place.PlaceRepository
import com.gritbus.hipchon.data.repository.user.UserRepository
import com.gritbus.hipchon.ui.feed.view.FeedFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val feedRepository: FeedRepository,
    private val commentRepository: CommentRepository,
    private val placeRepository: PlaceRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _feedData = MutableLiveData<FeedAllDataItem>()
    val feedData: LiveData<FeedAllDataItem> = _feedData

    private val _commentAllData = MutableLiveData<List<CommentAllDataItem>>()
    val commentAllData: LiveData<List<CommentAllDataItem>> = _commentAllData

    private val _isPostLike = MutableLiveData<Boolean>()
    val isPostLike: LiveData<Boolean> = _isPostLike

    private val _isPlaceSave = MutableLiveData<Boolean>()
    val isPlaceSave: LiveData<Boolean> = _isPlaceSave

    private val _userInfo = MutableLiveData<UserInfoData>()
    val userInfo: LiveData<UserInfoData> = _userInfo

    init {
        savedStateHandle.get<FeedAllDataItem>(FeedFragment.FEED_DETAIL_DATA)?.let {
            _feedData.value = it
            _isPostLike.value = it.isMypost
            _isPlaceSave.value = it.place.isMyplace
        }
    }

    fun getUserInfo() {
        viewModelScope.launch {
            userRepository.getUserData(UserData.platform, UserData.userLoginId)
                .onSuccess {
                    _userInfo.value = it
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "user error")
                }
        }
    }

    fun getCommentAll() {
        val postId = _feedData.value?.postId ?: return
        viewModelScope.launch {
            commentRepository.getPostCommentAllData(postId)
                .onSuccess {
                    _commentAllData.value = it
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "comment error")
                }
        }
    }

    fun getPlaceId(): Int? {
        return _feedData.value?.place?.placeId
    }

    fun savePlace() {
        val isCurrentSaveState = _isPlaceSave.value ?: return
        val placeId = _feedData.value?.place?.placeId ?: return
        viewModelScope.launch {
            when (isCurrentSaveState) {
                true -> {
                    placeRepository.deletePlace(UserData.userId, placeId)
                        .onSuccess {
                            _isPlaceSave.value = false
                        }
                        .onFailure {
                            Log.e(this.javaClass.name, it.message ?: "save error")
                        }
                }
                false -> {
                    placeRepository.savePlace(UserData.userId, placeId)
                        .onSuccess {
                            _isPlaceSave.value = true
                        }
                        .onFailure {
                            Log.e(this.javaClass.name, it.message ?: "save error")
                        }
                }
            }

        }
    }

    fun getPlaceHomepage(): String? {
        return _feedData.value?.place?.homepage
    }

    fun sendComment(comment: String) {
        val postId = _feedData.value?.postId ?: return
        viewModelScope.launch {
            commentRepository.postComment(CommentData(comment, postId, UserData.userId))
                .onSuccess {
                    getCommentAll()
                    updatePostData()
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "comment post error")
                }
        }
    }

    private fun updatePostData() {
        val postId = _feedData.value?.postId ?: return
        viewModelScope.launch {
            feedRepository.getFeedDetailData(UserData.userId, postId)
                .onSuccess {
                    _feedData.value = it
                }
                .onFailure {
                    Log.e(this.javaClass.name, it.message ?: "feed error")
                }
        }
    }

    fun likePost() {
        val isMypost = _feedData.value?.isMypost ?: return
        val postId = _feedData.value?.postId ?: return
        viewModelScope.launch {
            when (isMypost) {
                true -> {
                    feedRepository.deletePost(UserData.userId, postId)
                        .onSuccess {
                            _isPostLike.value = false
                        }
                        .onFailure {
                            Log.e(this.javaClass.name, it.message ?: "post like error")
                        }
                }
                false -> {
                    feedRepository.savePost(UserData.userId, postId)
                        .onSuccess {
                            _isPostLike.value = true
                        }
                        .onFailure {
                            Log.e(this.javaClass.name, it.message ?: "post like error")
                        }
                }
            }
        }
    }
}
