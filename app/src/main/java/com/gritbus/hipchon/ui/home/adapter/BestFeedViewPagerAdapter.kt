package com.gritbus.hipchon.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gritbus.hipchon.domain.model.Hashtag
import com.gritbus.hipchon.ui.home.view.BestFeedFragment

class BestFeedViewPagerAdapter(
    private val bestFeedAllData: List<Pair<String, Hashtag>>,
    private val fragmentManager: FragmentManager,
    private val lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = bestFeedAllData.size

    override fun createFragment(position: Int): Fragment {
        return BestFeedFragment.createBestFeedFragment(
            bestFeedAllData[position].first,
            bestFeedAllData[position].second
        )
    }
}
