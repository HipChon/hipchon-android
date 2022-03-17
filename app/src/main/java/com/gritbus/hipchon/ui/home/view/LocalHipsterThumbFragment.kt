package com.gritbus.hipchon.ui.home.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentLocalHipsterPickBinding
import com.gritbus.hipchon.utils.BaseViewUtil
import kotlin.properties.Delegates

class LocalHipsterThumbFragment :
    BaseViewUtil.BaseFragment<FragmentLocalHipsterPickBinding>(R.layout.fragment_local_hipster_pick) {

    private lateinit var imageUrl: String
    private var currentImagePosition by Delegates.notNull<Int>()
    private var imageCount by Delegates.notNull<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        initImageUrl()
        setView()
    }

    private fun initImageUrl() {
        (arguments?.get(LOCAL_HIPSTER_PICK_IMAGE_URL) as? String)?.let {
            imageUrl = it
        }
        (arguments?.get(LOCAL_HIPSTER_PICK_IMAGE_POSITION) as? Int)?.let {
            currentImagePosition = it + 1
        }
        (arguments?.get(LOCAL_HIPSTER_PICK_COUNT) as? Int)?.let {
            imageCount = it
        }
    }

    private fun setView() {
        binding.tvLocalHipsterPickThumb.text =
            resources.getString(R.string.home_banner, currentImagePosition, imageCount)
    }

    companion object {
        const val LOCAL_HIPSTER_PICK_IMAGE_URL =
            "com.gritbus.hipchon.ui.home.view LOCAL_HIPSTER_PICK_IMAGE_URL"
        const val LOCAL_HIPSTER_PICK_IMAGE_POSITION =
            "com.gritbus.hipchon.ui.home.view LOCAL_HIPSTER_PICK_IMAGE_POSITION"
        const val LOCAL_HIPSTER_PICK_COUNT =
            "com.gritbus.hipchon.ui.home.view LOCAL_HIPSTER_PICK_COUNT"

        fun createLocalHipsterThumbFragment(
            imageUrl: String,
            currentImagePosition: Int,
            imageCount: Int
        ): LocalHipsterThumbFragment {
            return LocalHipsterThumbFragment().apply {
                arguments = bundleOf(
                    LOCAL_HIPSTER_PICK_IMAGE_URL to imageUrl,
                    LOCAL_HIPSTER_PICK_IMAGE_POSITION to currentImagePosition,
                    LOCAL_HIPSTER_PICK_COUNT to imageCount
                )
            }
        }
    }
}
