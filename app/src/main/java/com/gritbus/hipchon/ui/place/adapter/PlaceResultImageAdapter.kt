package com.gritbus.hipchon.ui.place.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gritbus.hipchon.ui.place.view.PlaceResultFragment

class PlaceResultImageAdapter(
    private val imageUrl: List<String>,
    private val fragmentManager: FragmentManager,
    private val lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return imageUrl.size
    }

    override fun createFragment(position: Int): Fragment {
        return PlaceResultFragment.createPlaceResultFragment(
            imageUrl[position],
            position,
            imageUrl.size
        )
    }
}
