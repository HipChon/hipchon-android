package com.gritbus.hipchon.ui.feed.view

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.gritbus.hipchon.R
import com.gritbus.hipchon.data.model.feed.FeedAllDataItem
import com.gritbus.hipchon.databinding.ActivityFeedDetailBinding
import com.gritbus.hipchon.ui.feed.adapter.CommentAdapter
import com.gritbus.hipchon.ui.feed.adapter.FeedThumbAdapter
import com.gritbus.hipchon.ui.feed.viewmodel.FeedDetailViewModel
import com.gritbus.hipchon.ui.place.view.PlaceDetailActivity
import com.gritbus.hipchon.ui.place.view.PlaceResultActivity
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.ItemDecorationWithHorizontalSpacing
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
        setAdapter()
        setObserver()
        setOnClickListener()
        viewModel.getCommentAll()
    }

    private fun setAdapter() {
        commentAdapter = CommentAdapter()
        binding.rvFeedDetailComment.addItemDecoration(ItemDecorationWithVerticalSpacing(24))
        binding.rvFeedDetailComment.adapter = commentAdapter
    }

    private fun setObserver() {
        viewModel.feedData.observe(this) {
            setFeedView(it)
        }
        viewModel.isPlaceSave.observe(this) {
            setPlaceSaveView(it)
        }
        viewModel.commentAllData.observe(this) {
            commentAdapter.submitList(it)
        }
    }

    private fun setPlaceSaveView(isSave: Boolean) {
        binding.ivFeedDetailPlaceSave.background = when (isSave) {
            true -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_save_filled)
            false -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_save)
        }
        binding.ivFeedDetailPlaceSave.backgroundTintList =when (isSave) {
            true -> ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.primary_green))
            false -> ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.black))
        }
    }

    private fun setFeedView(feedData: FeedAllDataItem) {
        binding.tvFeedDetailTitle.text = feedData.place.name
        Glide.with(this)
            .load(feedData.user.image)
            .error(R.drawable.ic_profile_default_gray)
            .into(binding.ivFeedDetailProfile)
        binding.tvFeedDetailNickname.text = feedData.user.name
        binding.tvFeedDetailReviewCount.text = "${feedData.user.postCnt}번째 리뷰"
        binding.tvFeedDetailCreatedAt.text = feedData.time

        val feedThumbAdapter = FeedThumbAdapter(true)
        binding.rvFeedDetail.adapter = feedThumbAdapter
        binding.rvFeedDetail.addItemDecoration(ItemDecorationWithHorizontalSpacing(4))
        feedThumbAdapter.submitList(feedData.imageList)

        binding.tvFeedDetailContent.text = feedData.detail
        binding.tvFeedDetailPlaceTitle.text = feedData.place.name
        binding.tvFeedDetailPlaceAddress.text = "${feedData.place.category} • ${feedData.place.address}"

        binding.tvFeedDetailFavorite.text = feedData.likeCnt.toString()
//        binding.ivFeedDetailFavorite.background = when (feedData.isMyPost) {
//            true -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_save_filled)
//            false -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_save)
//        }
//        binding.ivFeedDetailFavorite.backgroundTintList =when (feedData.isMyPost) {
//            true -> ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.primary_green))
//            false -> ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.black))
//        }
        binding.tvFeedDetailComment.text = feedData.commentCnt.toString()
    }

    private fun setOnClickListener() {
        binding.mtFeedDetail.setNavigationOnClickListener {
            finish()
        }
        binding.clFeedDetailPlace.setOnClickListener {
            startActivity(Intent(baseContext, PlaceDetailActivity::class.java).apply {
                putExtra(PlaceResultActivity.PLACE_ID, viewModel.getPlaceId())
            })
        }
        binding.ivFeedDetailPlaceSave.setOnClickListener {
            viewModel.savePlace()
        }
        binding.ivFeedDetailPlaceShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, viewModel.getPlaceHomepage())
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            ContextCompat.startActivity(binding.root.context, shareIntent, null)
        }
    }
}
