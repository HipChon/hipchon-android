package com.gritbus.hipchon.ui.my.view

import android.os.Bundle
import android.view.View
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentMyReviewBinding
import com.gritbus.hipchon.ui.my.adapter.MyReviewAdapter
import com.gritbus.hipchon.utils.BaseViewUtil

class MyReviewFragment :
    BaseViewUtil.BaseFragment<FragmentMyReviewBinding>(R.layout.fragment_my_review) {

    private lateinit var myReviewAdapter: MyReviewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        setAdapter()
    }

    private fun setAdapter() {
        myReviewAdapter = MyReviewAdapter()
        binding.rvMyReview.adapter = myReviewAdapter
        myReviewAdapter.submitList(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    }
}
