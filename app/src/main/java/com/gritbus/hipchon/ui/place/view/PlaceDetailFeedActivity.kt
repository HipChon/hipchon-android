package com.gritbus.hipchon.ui.place.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityPlaceDetailFeedBinding
import com.gritbus.hipchon.ui.feed.adapter.FeedAdapter
import com.gritbus.hipchon.ui.feed.view.FeedCreateActivity
import com.gritbus.hipchon.ui.feed.view.FeedDetailActivity
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
        viewModel.getReviewData()
    }

    private fun setAdapter() {
        feedAdapter = FeedAdapter(true, ::moveToFeedDetail)
        binding.rvPlaceDetailFeed.adapter = feedAdapter
        binding.rvPlaceDetailFeed.addItemDecoration(ItemDecorationWithStroke(false))
    }

    private fun moveToFeedDetail() {
        startActivity(Intent(baseContext, FeedDetailActivity::class.java))
    }

    private fun setOnClickListener() {
        binding.mtPlaceDetailFeed.setNavigationOnClickListener {
            finish()
        }
        binding.mtPlaceDetailFeed.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.place_review_more -> {
                    startActivity(Intent(baseContext, FeedCreateActivity::class.java))
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
