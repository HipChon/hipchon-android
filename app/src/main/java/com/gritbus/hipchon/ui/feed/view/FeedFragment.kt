package com.gritbus.hipchon.ui.feed.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentReviewBinding
import com.gritbus.hipchon.ui.feed.adapter.FeedAdapter
import com.gritbus.hipchon.ui.feed.viewmodel.FeedViewModel
import com.gritbus.hipchon.ui.place.view.PlaceResultActivity
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.ItemDecorationWithStroke
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : BaseViewUtil.BaseFragment<FragmentReviewBinding>(R.layout.fragment_review) {

    private lateinit var feedAdapter: FeedAdapter
    private val viewModel: FeedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        setAdapter()
        setObserver()
        setOnClickListener()
        viewModel.initData()
    }

    private fun setAdapter() {
        feedAdapter = FeedAdapter(::moveToFeedDetail)
        binding.rvReview.adapter = feedAdapter
        binding.rvReview.addItemDecoration(ItemDecorationWithStroke())
    }

    private fun moveToFeedDetail() {
        startActivity(Intent(requireContext(), FeedDetailActivity::class.java))
    }

    private fun setObserver() {
        viewModel.reviewAllData.observe(viewLifecycleOwner) {
            feedAdapter.submitList(it)
        }
    }

    private fun setOnClickListener() {
        binding.mtReviewTitle.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.place_result_filter -> {
                    val feedFilterFragment = FeedFilterFragment().apply {
                        arguments =
                            bundleOf(PlaceResultActivity.PLACE_ORDER_TYPE to viewModel.reviewOrderType.value)
                    }
                    feedFilterFragment.show(
                        parentFragmentManager,
                        feedFilterFragment.tag
                    )
                    true
                }
                else -> false
            }
        }
        binding.fabReviewCreate.setOnClickListener {
            startActivity(Intent(requireContext(), FeedCreateActivity::class.java))
        }
    }
}
