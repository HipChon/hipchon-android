package com.gritbus.hipchon.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gritbus.hipchon.data.model.event.EventAllDataItem
import com.gritbus.hipchon.ui.home.view.BannerFragment

class BannerViewPagerAdapter(
    private val eventAllData: List<EventAllDataItem>,
    private val fragmentManager: FragmentManager,
    private val lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = eventAllData.size

    override fun createFragment(position: Int): Fragment {
        return BannerFragment.createBannerFragment(
            eventAllData[position],
            position + 1,
            eventAllData.size
        )
    }
}
