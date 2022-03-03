package com.gritbus.hipchon.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gritbus.hipchon.ui.home.view.BannerFragment

class BannerViewPagerAdapter(
    private val imageUrl: List<String>,
    private val fragmentManager: FragmentManager,
    private val lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = imageUrl.size

    override fun createFragment(position: Int): Fragment {
        return BannerFragment.createBannerFragment(
            imageUrl[position],
            position + 1,
            imageUrl.size
        )
    }
}
