package com.gritbus.hipchon.ui.my.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gritbus.hipchon.ui.my.view.MyReviewFragment

class MyAdapter(val typeList: List<String>, fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return MyReviewFragment.createMyReviewFragment(typeList[position])
    }
}
