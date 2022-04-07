package com.gritbus.hipchon.ui.place.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentPlaceResultBinding
import com.gritbus.hipchon.utils.BaseViewUtil
import kotlin.properties.Delegates

class PlaceResultFragment :
    BaseViewUtil.BaseFragment<FragmentPlaceResultBinding>(R.layout.fragment_place_result) {

    private lateinit var imageUrl: String
    private var currentImagePosition by Delegates.notNull<Int>()
    private var imageCount by Delegates.notNull<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        initImageUrl()
        setImage()
    }

    private fun initImageUrl() {
        (arguments?.get(PLACE_RESULT_THUMB_IMAGE_URL) as? String)?.let {
            imageUrl = it
        }
        (arguments?.get(PLACE_RESULT_THUMB_IMAGE_POSITION) as? Int)?.let {
            currentImagePosition = it + 1
        }
        (arguments?.get(PLACE_RESULT_THUMB_COUNT) as? Int)?.let {
            imageCount = it
        }
    }

    private fun setImage() {
        Glide.with(requireContext())
            .load(imageUrl)
            .into(binding.ivPlaceResult)

        binding.tvPlaceResult.text =
            resources.getString(R.string.home_banner, currentImagePosition, imageCount)
    }

    companion object {
        const val PLACE_RESULT_THUMB_IMAGE_URL =
            "com.gritbus.hipchon.ui.place.view PLACE_RESULT_THUMB_IMAGE_URL"
        const val PLACE_RESULT_THUMB_IMAGE_POSITION =
            "com.gritbus.hipchon.ui.place.view PLACE_RESULT_THUMB_IMAGE_POSITION"
        const val PLACE_RESULT_THUMB_COUNT =
            "com.gritbus.hipchon.ui.place.view PLACE_RESULT_THUMB_COUNT"

        fun createPlaceResultFragment(
            imageUrl: String,
            currentImagePosition: Int,
            imageCount: Int
        ): PlaceResultFragment {
            return PlaceResultFragment().apply {
                arguments = bundleOf(
                    PLACE_RESULT_THUMB_IMAGE_URL to imageUrl,
                    PLACE_RESULT_THUMB_IMAGE_POSITION to currentImagePosition,
                    PLACE_RESULT_THUMB_COUNT to imageCount
                )
            }
        }
    }
}
