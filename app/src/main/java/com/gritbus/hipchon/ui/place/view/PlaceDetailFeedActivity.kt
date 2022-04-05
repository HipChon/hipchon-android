package com.gritbus.hipchon.ui.place.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.gritbus.hipchon.R
import com.gritbus.hipchon.data.model.feed.FeedAllDataItem
import com.gritbus.hipchon.data.model.feed.FeedPlaceItem
import com.gritbus.hipchon.databinding.ActivityPlaceDetailFeedBinding
import com.gritbus.hipchon.ui.feed.adapter.FeedAdapter
import com.gritbus.hipchon.ui.feed.view.FeedCreateActivity
import com.gritbus.hipchon.ui.feed.view.FeedDetailActivity
import com.gritbus.hipchon.ui.feed.view.FeedFragment
import com.gritbus.hipchon.ui.place.viewmodel.PlaceDetailFeedViewModel
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.ItemDecorationWithStroke
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceDetailFeedActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityPlaceDetailFeedBinding>(R.layout.activity_place_detail_feed) {

    private lateinit var feedAdapter: FeedAdapter
    private val viewModel: PlaceDetailFeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
        setAdapter()
        setOnClickListener()
        setObserver()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getReviewData()
    }

    private fun setAdapter() {
        feedAdapter = FeedAdapter(true, ::moveToFeedDetail, ::moveToPlaceDetail, ::likePost, ::savePlace)
        binding.rvPlaceDetailFeed.adapter = feedAdapter
        binding.rvPlaceDetailFeed.addItemDecoration(ItemDecorationWithStroke(false))
    }

    private fun moveToFeedDetail(feedData: FeedAllDataItem) {
        startActivity(Intent(baseContext, FeedDetailActivity::class.java).apply {
            putExtra(FeedFragment.FEED_DETAIL_DATA, feedData)
        })
    }

    private fun moveToPlaceDetail(placeId: Int) {
        Log.e(this.javaClass.name, "제공하지 않는 기능")
    }

    private fun likePost(postId: Int, isMypost: Boolean) {
        viewModel.likePost(postId, isMypost)
    }

    private fun savePlace(placeData: FeedPlaceItem) {
        Log.e(this.javaClass.name, "제공하지 않는 기능")
    }

    private fun setOnClickListener() {
        binding.mtPlaceDetailFeed.setNavigationOnClickListener {
            finish()
        }
        binding.mtPlaceDetailFeed.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.place_review_more -> {
                    startActivity(Intent(baseContext, FeedCreateActivity::class.java).apply {
                        putExtra(PlaceDetailActivity.FEED_POST_INFO, viewModel.getPostData())
                    })
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun setObserver() {
        viewModel.placeReviewAllData.observe(this) {
            feedAdapter.submitList(it)
            binding.ctlPlaceDetailFeed.title = "${feedAdapter.currentList.size}개의 리뷰"
        }
    }
}
