package com.gritbus.hipchon.ui.place.view

import android.content.Intent
import android.os.Bundle
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityPlaceDetailFeedBinding
import com.gritbus.hipchon.ui.feed.adapter.FeedAdapter
import com.gritbus.hipchon.ui.feed.view.FeedCreateActivity
import com.gritbus.hipchon.ui.feed.view.FeedDetailActivity
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.ItemDecorationWithStroke

class PlaceDetailFeedActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityPlaceDetailFeedBinding>(R.layout.activity_place_detail_feed) {

    private lateinit var feedAdapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
        setAdapter()
        setOnClickListener()
        initData()
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

    private fun initData() {
        feedAdapter.submitList(listOf(1, 2, 3, 4, 5))
        binding.mtPlaceDetailFeed.title = "5개의 리뷰"
    }
}
