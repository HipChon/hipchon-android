package com.gritbus.hipchon.ui.home.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentBannerBinding
import com.gritbus.hipchon.utils.BaseViewUtil
import kotlin.properties.Delegates

class BannerFragment : BaseViewUtil.BaseFragment<FragmentBannerBinding>(R.layout.fragment_banner) {

    private lateinit var imageUrl: String
    private var currentPosition by Delegates.notNull<Int>()
    private var imageCount by Delegates.notNull<Int>()

    interface OnSwipeListener {
        fun onSwipe(position: Int, imageCount: Int)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        initImageUrl()
        setBannerImage()
    }

    override fun onResume() {
        super.onResume()
        (parentFragment as? HomeFragment)?.onSwipe(currentPosition, imageCount)
    }

    private fun initImageUrl() {
        (arguments?.get(BANNER_IMAGE_URL) as? String)?.let {
            imageUrl = it
        }
        (arguments?.get(BANNER_CURRENT_POSITION) as? Int)?.let {
            currentPosition = it
        }
        (arguments?.get(BANNER_IMAGE_COUNT) as? Int)?.let {
            imageCount = it
        }
    }

    private fun setBannerImage() {
        Glide.with(requireContext())
            .load(imageUrl)
            .into(binding.ivHomeBanner)
    }

    companion object {
        const val BANNER_IMAGE_URL = "com.gritbus.hipchon.ui.home.view BANNER_IMAGE_URL"
        const val BANNER_CURRENT_POSITION =
            "com.gritbus.hipchon.ui.home.view BANNER_CURRENT_POSITION"
        const val BANNER_IMAGE_COUNT = "com.gritbus.hipchon.ui.home.view BANNER_IMAGE_COUNT"

        fun createBannerFragment(
            imageUrl: String,
            currentImagePosition: Int,
            imageCount: Int
        ): BannerFragment {
            return BannerFragment().apply {
                arguments = bundleOf(
                    BANNER_IMAGE_URL to imageUrl,
                    BANNER_CURRENT_POSITION to currentImagePosition,
                    BANNER_IMAGE_COUNT to imageCount
                )
            }
        }
    }
}
