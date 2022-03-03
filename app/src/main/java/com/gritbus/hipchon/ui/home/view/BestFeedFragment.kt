package com.gritbus.hipchon.ui.home.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentBestFeedBinding
import com.gritbus.hipchon.domain.model.Hashtag
import com.gritbus.hipchon.utils.BaseViewUtil

class BestFeedFragment :
    BaseViewUtil.BaseFragment<FragmentBestFeedBinding>(R.layout.fragment_best_feed) {

    private lateinit var feedTitle: String
    private lateinit var feedHashtag: Hashtag
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        initFeedData()
        setView()
    }

    private fun initFeedData() {
        (arguments?.get(BEST_FEED_TITLE) as? String)?.let {
            feedTitle = it
        }
        (arguments?.get(BEST_FEED_HASHTAG) as? Hashtag)?.let {
            feedHashtag = it
        }
    }

    private fun setView() {
        binding.tvBestFeedTitle.text = feedTitle

        val hashtagDrawable = when(feedHashtag){
            Hashtag.FIRE -> R.drawable.ic_fire
            Hashtag.WATER -> R.drawable.ic_water
            Hashtag.FIELD -> R.drawable.ic_field
            Hashtag.VACATION -> R.drawable.ic_vacation
            Hashtag.NOTHING -> return
        }

        binding.ivBestFeedHashtag.setImageResource(hashtagDrawable)
    }

    companion object {
        const val BEST_FEED_TITLE = "com.gritbus.hipchon.ui.home.view BEST_FEED_TITLE"
        const val BEST_FEED_HASHTAG = "com.gritbus.hipchon.ui.home.view BEST_FEED_HASHTAG"

        fun createBestFeedFragment(title: String, hashtag: Hashtag): BestFeedFragment {
            return BestFeedFragment().apply {
                arguments = bundleOf(BEST_FEED_TITLE to title, BEST_FEED_HASHTAG to hashtag)
            }
        }
    }
}