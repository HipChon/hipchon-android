package com.gritbus.hipchon.ui.place.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityPlaceDetailBinding
import com.gritbus.hipchon.ui.place.viewmodel.PlaceDetailViewModel
import com.gritbus.hipchon.utils.BaseViewUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceDetailActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityPlaceDetailBinding>(R.layout.activity_place_detail) {

    private val viewModel: PlaceDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
        setMapScrolling()
        setOnClickListener()
        setObserver()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setMapScrolling() {
        binding.ivPlaceDetailMapPreview.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.nsvPlaceDetail.requestDisallowInterceptTouchEvent(true)
                    false
                }
                MotionEvent.ACTION_UP -> {
                    binding.nsvPlaceDetail.requestDisallowInterceptTouchEvent(false)
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    binding.nsvPlaceDetail.requestDisallowInterceptTouchEvent(true)
                    false
                }
                else -> {
                    true
                }
            }
        }
    }

    private fun setOnClickListener() {
        binding.mtPlaceDetail.setNavigationOnClickListener {
            finish()
        }
        binding.ivPlaceDetailCall.setOnClickListener {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:01012345678")))
        }
        binding.ivPlaceDetailShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        binding.ivPlaceDetailFeed.setOnClickListener {
            binding.nsvPlaceDetail.smoothScrollTo(0, binding.ivPlaceDetailReview.y.toInt())
        }
        binding.ivPlaceDetailSave.setOnClickListener {
            viewModel.setSave()
        }
        binding.acbPlaceDetailLink.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.naver.com")))
        }
    }

    private fun setObserver() {
        viewModel.isSave.observe(this) {
            updateSaveView(it)
        }
    }

    private fun updateSaveView(isSave: Boolean) {
        when (isSave) {
            true -> {
                binding.ivPlaceDetailSave.setImageResource(R.drawable.ic_save_filled)
                binding.ivPlaceDetailSave.imageTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(baseContext, R.color.primary_green)
                    )
            }
            false -> {
                binding.ivPlaceDetailSave.setImageResource(R.drawable.ic_save)
                binding.ivPlaceDetailSave.imageTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(baseContext, R.color.black))
            }
        }
    }
}