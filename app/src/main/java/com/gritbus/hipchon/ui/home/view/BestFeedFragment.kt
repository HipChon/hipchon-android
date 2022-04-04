package com.gritbus.hipchon.ui.home.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.gritbus.hipchon.R
import com.gritbus.hipchon.data.model.feed.FeedBestAllDataItem
import com.gritbus.hipchon.databinding.FragmentBestFeedBinding
import com.gritbus.hipchon.domain.model.Hashtag
import com.gritbus.hipchon.utils.BaseViewUtil

class BestFeedFragment :
    BaseViewUtil.BaseFragment<FragmentBestFeedBinding>(R.layout.fragment_best_feed) {

    private lateinit var feedData: FeedBestAllDataItem

    interface OnClickListener {
        fun onClick(postId: Int)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        initFeedData()
        setOnClickListener()
        setView()
    }

    private fun initFeedData() {
        (arguments?.get(BEST_FEED_DATA) as? FeedBestAllDataItem)?.let {
            feedData = it
        }
    }

    private fun setOnClickListener() {
        binding.root.setOnClickListener {
            (parentFragment as? HomeFragment)?.onClick(feedData.postId)
        }
    }

    private fun setView() {
        binding.tvBestFeedTitle.text = feedData.title

        val hashtagDrawable = when (feedData.hashtag.name) {
            Hashtag.FIRE.value -> R.drawable.ic_fire
            Hashtag.WATER.value -> R.drawable.ic_water
            Hashtag.FIELD.value -> R.drawable.ic_field
            Hashtag.VACATION.value -> R.drawable.ic_vacation
            else -> return
        }

        binding.ivBestFeedHashtag.setImageResource(hashtagDrawable)
    }

    companion object {
        const val BEST_FEED_DATA = "com.gritbus.hipchon.ui.home.view BEST_FEED_DATA"

        fun createBestFeedFragment(feedData: FeedBestAllDataItem): BestFeedFragment {
            return BestFeedFragment().apply {
                arguments = bundleOf(BEST_FEED_DATA to feedData)
            }
        }
    }
}
