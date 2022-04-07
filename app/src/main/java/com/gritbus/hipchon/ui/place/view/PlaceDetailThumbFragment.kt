package com.gritbus.hipchon.ui.place.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentPlaceDetailThumbBinding
import com.gritbus.hipchon.utils.BaseViewUtil

class PlaceDetailThumbFragment :
    BaseViewUtil.BaseFragment<FragmentPlaceDetailThumbBinding>(R.layout.fragment_place_detail_thumb) {

    private lateinit var imageUrl: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        initImageUrl()
        setImage()
    }

    private fun initImageUrl() {
        (arguments?.get(PLACE_DETAIL_THUMB_IMAGE_URL) as? String)?.let {
            imageUrl = it
        }
    }

    private fun setImage() {
        Glide.with(requireContext())
            .load(imageUrl)
            .into(binding.ivPlaceDetailThumb)
    }

    companion object {
        const val PLACE_DETAIL_THUMB_IMAGE_URL =
            "com.gritbus.hipchon.ui.place.view PLACE_DETAIL_THUMB_IMAGE_URL"

        fun createPlaceDetailThumbFragment(
            imageUrl: String
        ): PlaceDetailThumbFragment {
            return PlaceDetailThumbFragment().apply {
                arguments = bundleOf(PLACE_DETAIL_THUMB_IMAGE_URL to imageUrl)
            }
        }
    }
}
