package com.gritbus.hipchon.ui.my.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gritbus.hipchon.ui.my.view.MyCommentFragment
import com.gritbus.hipchon.ui.my.view.MyFragment.Companion.MY_FEED
import com.gritbus.hipchon.ui.my.view.MyFragment.Companion.MY_LIKE_FEED
import com.gritbus.hipchon.ui.my.view.MyReviewFragment

class MyAdapter(private val typeList: List<String>, fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(typeList[position]){
            MY_FEED -> {
                MyReviewFragment.createMyReviewFragment(typeList[position])
            }
            MY_LIKE_FEED -> {
                MyReviewFragment.createMyReviewFragment(typeList[position])
            }
            else -> {
                MyCommentFragment.createMyCommentFragment(typeList[position])
            }
        }
    }
}
