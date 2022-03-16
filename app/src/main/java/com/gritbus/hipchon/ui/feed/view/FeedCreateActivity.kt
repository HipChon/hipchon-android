package com.gritbus.hipchon.ui.feed.view

import android.os.Bundle
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityFeedCreateBinding
import com.gritbus.hipchon.utils.BaseViewUtil

class FeedCreateActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityFeedCreateBinding>(R.layout.activity_feed_create) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
    }
}
