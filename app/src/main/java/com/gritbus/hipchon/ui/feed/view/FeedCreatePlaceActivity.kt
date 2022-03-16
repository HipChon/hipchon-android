package com.gritbus.hipchon.ui.feed.view

import android.os.Bundle
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityFeedCreatePlaceBinding
import com.gritbus.hipchon.utils.BaseViewUtil

class FeedCreatePlaceActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityFeedCreatePlaceBinding>(R.layout.activity_feed_create_place) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {}
}
