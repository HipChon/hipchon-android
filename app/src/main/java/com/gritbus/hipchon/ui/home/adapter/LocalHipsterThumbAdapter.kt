package com.gritbus.hipchon.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gritbus.hipchon.ui.home.view.LocalHipsterThumbFragment

class LocalHipsterThumbAdapter(
    private val thumbList: List<String>,
    private val fragmentManager: FragmentManager,
    private val lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = thumbList.size

    override fun createFragment(position: Int): Fragment {
        return LocalHipsterThumbFragment.createLocalHipsterThumbFragment(
            thumbList[position],
            position,
            thumbList.size
        )
    }
}
