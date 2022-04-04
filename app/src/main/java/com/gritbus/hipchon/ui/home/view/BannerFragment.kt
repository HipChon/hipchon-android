package com.gritbus.hipchon.ui.home.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.gritbus.hipchon.R
import com.gritbus.hipchon.data.model.event.EventAllDataItem
import com.gritbus.hipchon.databinding.FragmentBannerBinding
import com.gritbus.hipchon.utils.BaseViewUtil
import kotlin.properties.Delegates

class BannerFragment : BaseViewUtil.BaseFragment<FragmentBannerBinding>(R.layout.fragment_banner) {

    private lateinit var bannerData: EventAllDataItem
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
        setBannerView()
    }

    override fun onResume() {
        super.onResume()
        (parentFragment as? HomeFragment)?.onSwipe(currentPosition, imageCount)
    }

    private fun initImageUrl() {
        (arguments?.get(BANNER_DATA) as? EventAllDataItem)?.let {
            bannerData = it
        }
        (arguments?.get(BANNER_CURRENT_POSITION) as? Int)?.let {
            currentPosition = it
        }
        (arguments?.get(BANNER_IMAGE_COUNT) as? Int)?.let {
            imageCount = it
        }
    }

    private fun setBannerView() {
        Glide.with(requireContext())
            .load(bannerData.thumbnail)
            .into(binding.ivHomeBanner)

        bannerData.url?.let { url ->
            binding.ivHomeBanner.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
        }
    }

    companion object {
        const val BANNER_DATA = "com.gritbus.hipchon.ui.home.view BANNER_DATA"
        const val BANNER_CURRENT_POSITION =
            "com.gritbus.hipchon.ui.home.view BANNER_CURRENT_POSITION"
        const val BANNER_IMAGE_COUNT = "com.gritbus.hipchon.ui.home.view BANNER_IMAGE_COUNT"

        fun createBannerFragment(
            bannerData: EventAllDataItem,
            currentImagePosition: Int,
            imageCount: Int
        ): BannerFragment {
            return BannerFragment().apply {
                arguments = bundleOf(
                    BANNER_DATA to bannerData,
                    BANNER_CURRENT_POSITION to currentImagePosition,
                    BANNER_IMAGE_COUNT to imageCount
                )
            }
        }
    }
}
