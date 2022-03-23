package com.gritbus.hipchon.ui.feed.view

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentFeedFilterBinding
import com.gritbus.hipchon.domain.model.FeedOrderType
import com.gritbus.hipchon.ui.feed.view.FeedFragment.Companion.FEED_ORDER_TYPE
import com.gritbus.hipchon.ui.feed.viewmodel.FeedViewModel
import com.gritbus.hipchon.utils.BaseViewUtil

class FeedFilterFragment :
    BaseViewUtil.BaseBottomSheetDialogFragment<FragmentFeedFilterBinding>(R.layout.fragment_feed_filter) {

    private lateinit var initialOrderType: FeedOrderType
    private lateinit var orderType: FeedOrderType
    private val viewModel: FeedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        initialOrderType = arguments?.get(FEED_ORDER_TYPE) as FeedOrderType
        orderType = initialOrderType
        setOrderTypeView()
        setOnClickListener()
    }

    private fun setOrderTypeView() {
        setApplyView()

        when (orderType) {
            FeedOrderType.RECENT -> {
                setRecentOptionView(true)
                setLikeOptionView(false)
            }
            FeedOrderType.LIKE -> {
                setRecentOptionView(false)
                setLikeOptionView(true)
            }
        }
    }

    private fun setApplyView() {
        if (initialOrderType != orderType) {
            binding.tvFeedFilterApply.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_quick_search_apply_active)
            binding.tvFeedFilterApply.setTextColor(
                ContextCompat.getColor(requireContext(), R.color.white)
            )
        } else {
            binding.tvFeedFilterApply.background =
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.bg_quick_search_apply_default
                )
            binding.tvFeedFilterApply.setTextColor(
                ContextCompat.getColor(requireContext(), R.color.black)
            )
        }
    }

    private fun setRecentOptionView(isActive: Boolean) {
        setOptionView(binding.tvFeedFilterRecent, binding.ivFeedFilterRecent, isActive)
    }

    private fun setLikeOptionView(isActive: Boolean) {
        setOptionView(binding.tvFeedFilterLike, binding.ivFeedFilterLike, isActive)
    }

    private fun setOptionView(textView: TextView, imageView: ImageView, isActive: Boolean) {
        imageView.visibility = when (isActive) {
            true -> View.VISIBLE
            false -> View.INVISIBLE
        }
        textView.setTypeface(
            null, when (isActive) {
                true -> Typeface.BOLD
                false -> Typeface.NORMAL
            }
        )
    }

    private fun setOnClickListener() {
        binding.tvFeedFilterRecent.setOnClickListener {
            orderType = FeedOrderType.RECENT
            setOrderTypeView()
        }
        binding.tvFeedFilterLike.setOnClickListener {
            orderType = FeedOrderType.LIKE
            setOrderTypeView()
        }
        binding.tvFeedFilterApply.setOnClickListener {
            viewModel.setOrderType(orderType)
            dialog?.dismiss()
        }
    }
}
