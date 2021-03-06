package com.gritbus.hipchon.ui.feed.view

import android.app.AlertDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.gritbus.hipchon.R
import com.gritbus.hipchon.data.model.feed.FeedAllDataItem
import com.gritbus.hipchon.data.model.user.UserInfoData
import com.gritbus.hipchon.databinding.ActivityFeedDetailBinding
import com.gritbus.hipchon.ui.feed.adapter.CommentAdapter
import com.gritbus.hipchon.ui.feed.adapter.FeedThumbAdapter
import com.gritbus.hipchon.ui.feed.viewmodel.FeedDetailViewModel
import com.gritbus.hipchon.ui.place.view.PlaceDetailActivity
import com.gritbus.hipchon.ui.place.view.PlaceResultActivity
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.ItemDecorationWithHorizontalSpacing
import com.gritbus.hipchon.utils.ItemDecorationWithVerticalSpacing
import com.gritbus.hipchon.utils.dateToFormattedString
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
        viewModel.getUserInfo()
    }

    private fun setAdapter() {
        commentAdapter = CommentAdapter(::reportComment, ::reportCommentUser)
        binding.rvFeedDetailComment.addItemDecoration(ItemDecorationWithVerticalSpacing(24))
        binding.rvFeedDetailComment.adapter = commentAdapter
    }

    private fun reportCommentUser(userId: Int) {
        showUserReportDialog(userId)
    }

    private fun showUserReportDialog(userId: Int) {
        val dialog = AlertDialog.Builder(this).apply {
            setTitle("?????? ????????????")
            setMessage("?????? ????????? ?????????????????????????")
            setNegativeButton("??????") { _, _ -> }
            setPositiveButton("??????") { _, _ ->
                viewModel.reportCommentUser(userId)
            }
        }
        dialog.create()
        dialog.show()
    }

    private fun reportComment(commentId: Int) {
        showCommentReportDialog(commentId)
    }

    private fun showCommentReportDialog(commentId: Int) {
        val dialog = AlertDialog.Builder(this).apply {
            setTitle("?????? ????????????")
            setMessage("?????? ????????? ?????????????????????????")
            setNegativeButton("??????") { _, _ -> }
            setPositiveButton("??????") { _, _ ->
                viewModel.reportComment(commentId)
            }
        }
        dialog.create()
        dialog.show()
    }

    private fun setObserver() {
        viewModel.feedData.observe(this) {
            setFeedView(it)
        }
        viewModel.isPostLike.observe(this) {
            setPostLikeView(it)
        }
        viewModel.isPlaceSave.observe(this) {
            setPlaceSaveView(it)
        }
        viewModel.commentAllData.observe(this) {
            commentAdapter.submitList(it)
            binding.etFeedCommentInput.setText("")
        }
        viewModel.userInfo.observe(this) {
            setUserView(it)
        }
        viewModel.isReportSuccess.observe(this) {
            if (it) {
                finish()
            }
        }
    }

    private fun setPostLikeView(isMypost: Boolean) {
        binding.ivFeedDetailFavorite.background = when (isMypost) {
            true -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_favorite_filled)
            false -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_favorite)
        }
        binding.tvFeedDetailFavorite.text = viewModel.getUpdatePostLikeCount().toString()
    }

    private fun setPlaceSaveView(isSave: Boolean) {
        binding.ivFeedDetailPlaceSave.background = when (isSave) {
            true -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_save_filled)
            false -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_save)
        }
        binding.ivFeedDetailPlaceSave.backgroundTintList = when (isSave) {
            true -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.primary_green
                )
            )
            false -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.black
                )
            )
        }
    }

    private fun setFeedView(feedData: FeedAllDataItem) {
        binding.tvFeedDetailTitle.text = feedData.place.name
        Glide.with(this)
            .load(feedData.user.image)
            .circleCrop()
            .error(R.drawable.ic_profile_default_gray)
            .into(binding.ivFeedDetailProfile)
        binding.tvFeedDetailNickname.text = feedData.user.name
        binding.tvFeedDetailReviewCount.text = "${feedData.user.postCnt}?????? ??????"
        binding.tvFeedDetailCreatedAt.text = feedData.time.dateToFormattedString()

        if (!feedData.imageList.isNullOrEmpty()) {
            val feedThumbAdapter = FeedThumbAdapter(true)
            binding.rvFeedDetail.adapter = feedThumbAdapter
            binding.rvFeedDetail.addItemDecoration(ItemDecorationWithHorizontalSpacing(4))
            feedThumbAdapter.submitList(feedData.imageList)
            binding.rvFeedDetail.visibility = View.VISIBLE
        }

        binding.tvFeedDetailContent.text = feedData.detail
        binding.tvFeedDetailPlaceTitle.text = feedData.place.name
        binding.tvFeedDetailPlaceAddress.text =
            "${feedData.place.category} ??? ${feedData.place.address}"

        binding.tvFeedDetailFavorite.text = feedData.likeCnt.toString()
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
        binding.ivFeedDetailFavorite.setOnClickListener {
            viewModel.likePost()
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
        binding.tvFeedDetailCommentInput.setOnClickListener {
            binding.etFeedCommentInput.text.toString()?.let {
                if (it.isNotEmpty()) viewModel.sendComment(it)
            }
        }
        binding.tvFeedDetailReport.setOnClickListener {
            showFeedReportDialog()
        }
        binding.ivFeedDetailProfile.setOnClickListener {
            showFeedUserReportDialog()
        }
    }

    private fun showFeedReportDialog() {
        val dialog = AlertDialog.Builder(this).apply {
            setTitle("????????? ????????????")
            setMessage("?????? ???????????? ?????????????????????????")
            setNegativeButton("??????") { _, _ -> }
            setPositiveButton("??????") { _, _ ->
                viewModel.reportFeed()
            }
        }
        dialog.create()
        dialog.show()
    }

    private fun showFeedUserReportDialog() {
        val dialog = AlertDialog.Builder(this).apply {
            setTitle("?????? ????????????")
            setMessage("?????? ????????? ?????????????????????????")
            setNegativeButton("??????") { _, _ -> }
            setPositiveButton("??????") { _, _ ->
                viewModel.reportFeedUser()
            }
        }
        dialog.create()
        dialog.show()
    }


    private fun setUserView(userData: UserInfoData) {
        Glide.with(baseContext)
            .load(userData.image)
            .circleCrop()
            .error(R.drawable.ic_profile_default_gray)
            .into(binding.ivFeedCommentInputProfile)
    }
}
