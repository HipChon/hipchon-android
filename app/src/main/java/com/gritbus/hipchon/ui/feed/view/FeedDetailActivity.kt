package com.gritbus.hipchon.ui.feed.view

import android.os.Bundle
import androidx.activity.viewModels
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityFeedDetailBinding
import com.gritbus.hipchon.ui.feed.adapter.CommentAdapter
import com.gritbus.hipchon.ui.feed.viewmodel.FeedDetailViewModel
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.ItemDecorationWithVerticalSpacing
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedDetailActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityFeedDetailBinding>(R.layout.activity_feed_detail) {

    private val viewModel: FeedDetailViewModel by viewModels()
    private lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
        binding.rvFeedDetailComment.addItemDecoration(ItemDecorationWithVerticalSpacing(24))
        viewModel.getCommentAll()
        setAdapter()
        setObserver()
    }

    private fun setAdapter() {
        commentAdapter = CommentAdapter()
        binding.rvFeedDetailComment.adapter = commentAdapter
    }

    private fun setObserver() {
        viewModel.commentAllData.observe(this) {
            commentAdapter.submitList(it)
        }
    }
}
