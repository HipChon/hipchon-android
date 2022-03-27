package com.gritbus.hipchon.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gritbus.hipchon.data.model.feed.FeedBestAllDataItem
import com.gritbus.hipchon.ui.home.view.BestFeedFragment

class BestFeedViewPagerAdapter(
    private val bestFeedAllData: List<FeedBestAllDataItem>,
    private val fragmentManager: FragmentManager,
    private val lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = bestFeedAllData.size

    override fun createFragment(position: Int): Fragment {
        return BestFeedFragment.createBestFeedFragment(
            bestFeedAllData[position].title ?: "데이터가 업데이트 필요",
            bestFeedAllData[position].hashtagName
        )
    }
}
