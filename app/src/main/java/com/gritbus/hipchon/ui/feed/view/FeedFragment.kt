package com.gritbus.hipchon.ui.feed.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentReviewBinding
import com.gritbus.hipchon.ui.feed.adapter.FeedAdapter
import com.gritbus.hipchon.ui.feed.viewmodel.FeedViewModel
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.ItemDecorationWithStroke
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : BaseViewUtil.BaseFragment<FragmentReviewBinding>(R.layout.fragment_review) {

    private lateinit var feedAdapter: FeedAdapter
    private val viewModel: FeedViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        setAdapter()
        setObserver()
        viewModel.initData()
    }

    private fun setAdapter() {
        feedAdapter = FeedAdapter()
        binding.rvReview.adapter = feedAdapter
        binding.rvReview.addItemDecoration(ItemDecorationWithStroke())
    }

    private fun setObserver() {
        viewModel.reviewAllData.observe(viewLifecycleOwner){
            feedAdapter.submitList(it)
        }
    }
}
