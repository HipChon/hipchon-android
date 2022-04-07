package com.gritbus.hipchon.ui.save.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gritbus.hipchon.ui.save.view.SavePlaceFragment

class SaveAdapter(
    private val titleList: List<String>,
    fragment: Fragment
): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return SavePlaceFragment.createSavePlaceFragment(titleList[position])
    }
}
