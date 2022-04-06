package com.gritbus.hipchon.ui.feed.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.gritbus.hipchon.R
import com.gritbus.hipchon.data.model.feed.FeedAllDataItem
import com.gritbus.hipchon.data.model.feed.FeedPlaceItem
import com.gritbus.hipchon.databinding.FragmentReviewBinding
import com.gritbus.hipchon.ui.feed.adapter.FeedAdapter
import com.gritbus.hipchon.ui.feed.viewmodel.FeedViewModel
import com.gritbus.hipchon.ui.place.view.PlaceDetailActivity
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
    }

    override fun onResume() {
        super.onResume()
        viewModel.initData()
    }

    private fun setAdapter() {
        feedAdapter = FeedAdapter(false, ::moveToFeedDetail, ::moveToPlaceDetail,::likePost, ::savePlace, ::reportPost)
        binding.rvReview.adapter = feedAdapter
        binding.rvReview.addItemDecoration(ItemDecorationWithStroke(false))
    }

    private fun reportPost(postId: Int) {
        showPostReportDialog(postId)
    }

    private fun showPostReportDialog(postId: Int) {
        val dialog = AlertDialog.Builder(requireContext()).apply {
            setTitle("게시글 신고하기")
            setMessage("해당 게시글을 신고하시겠습니까?")
            setNegativeButton("취소") { _, _ -> }
            setPositiveButton("신고") { _, _ ->
                viewModel.reportFeed(postId)
            }
        }
        dialog.create()
        dialog.show()
    }

    private fun likePost(postId: Int, isMypost: Boolean) {
        viewModel.likePost(postId, isMypost)
    }

    private fun moveToFeedDetail(feedData: FeedAllDataItem) {
        startActivity(Intent(requireContext(), FeedDetailActivity::class.java).apply {
            putExtra(FEED_DETAIL_DATA, feedData)
        })
    }

    private fun moveToPlaceDetail(placeId: Int) {
        startActivity(Intent(requireContext(), PlaceDetailActivity::class.java).apply {
            putExtra(PlaceResultActivity.PLACE_ID, placeId)
        })
    }

    private fun savePlace(placeData: FeedPlaceItem) {
        viewModel.savePlace(placeData)
    }

    private fun setObserver() {
        viewModel.reviewAllData.observe(viewLifecycleOwner) {
            feedAdapter.submitList(it)
        }
        viewModel.reviewOrderType.observe(viewLifecycleOwner) {
            viewModel.initData()
        }
    }

    private fun setOnClickListener() {
        binding.mtReviewTitle.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.place_result_filter -> {
                    val feedFilterFragment = FeedFilterFragment().apply {
                        arguments =
                            bundleOf(FEED_ORDER_TYPE to viewModel.reviewOrderType.value)
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
            startActivity(Intent(requireContext(), FeedCreatePlaceActivity::class.java))
        }
    }

    companion object {
        const val FEED_ORDER_TYPE = "com.gritbus.hipchon.ui.feed.view FEED_ORDER_TYPE"
        const val FEED_DETAIL_DATA = "com.gritbus.hipchon.ui.feed.view FEED_DETAIL_DATA"
    }
}
