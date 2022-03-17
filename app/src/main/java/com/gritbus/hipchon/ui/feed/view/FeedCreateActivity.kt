package com.gritbus.hipchon.ui.feed.view

import android.os.Bundle
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityFeedCreateBinding
import com.gritbus.hipchon.ui.feed.adapter.FeedKeywordAdapter
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.ItemDecorationWithHorizontalSpacing

class FeedCreateActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityFeedCreateBinding>(R.layout.activity_feed_create) {

    private lateinit var keywordAdapter: FeedKeywordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
        setKeywordAdapter()
    }

    private fun setKeywordAdapter() {
        keywordAdapter = FeedKeywordAdapter()
        binding.rvFeedCreateKeyword.adapter = keywordAdapter
        binding.rvFeedCreateKeyword.addItemDecoration(ItemDecorationWithHorizontalSpacing(16))

        keywordAdapter.submitList(listOf("시설", "분위기", "만족도"))
    }
}
