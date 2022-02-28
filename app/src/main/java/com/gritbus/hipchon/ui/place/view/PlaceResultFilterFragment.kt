package com.gritbus.hipchon.ui.place.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentPlaceResultFilterBinding
import com.gritbus.hipchon.domain.model.PlaceOrderType
import com.gritbus.hipchon.utils.BaseViewUtil


class PlaceResultFilterFragment :
    BaseViewUtil.BaseBottomSheetDialogFragment<FragmentPlaceResultFilterBinding>(R.layout.fragment_place_result_filter) {

    private lateinit var initialOrderType: PlaceOrderType
    private lateinit var orderType: PlaceOrderType

    private val orderTypeCallback: OnOrderTypeClickListener?
        get() = activity as? OnOrderTypeClickListener

    interface OnOrderTypeClickListener {
        fun onClick(orderType: PlaceOrderType)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        initialOrderType = arguments?.get(PlaceResultActivity.PLACE_ORDER_TYPE) as PlaceOrderType
        orderType = initialOrderType
        setOrderTypeView()
        setOnClickListener()
    }

    private fun setOrderTypeView() {
        setApplyView()

        when (orderType) {
            PlaceOrderType.SAVE -> {
                setSaveOptionView(true)
                setFeedOptionView(false)
                setDistanceOptionView(false)
            }
            PlaceOrderType.FEED -> {
                setSaveOptionView(false)
                setFeedOptionView(true)
                setDistanceOptionView(false)
            }
            PlaceOrderType.DISTANCE -> {
                setSaveOptionView(false)
                setFeedOptionView(false)
                setDistanceOptionView(true)
            }
        }
    }

    private fun setApplyView() {
        if (initialOrderType != orderType) {
            binding.tvPlaceResultFilterApply.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_quick_search_apply_active)
            binding.tvPlaceResultFilterApply.setTextColor(
                ContextCompat.getColor(requireContext(), R.color.white)
            )
        } else {
            binding.tvPlaceResultFilterApply.background =
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.bg_quick_search_apply_default
                )
            binding.tvPlaceResultFilterApply.setTextColor(
                ContextCompat.getColor(requireContext(), R.color.black)
            )
        }
    }

    private fun setSaveOptionView(isActive: Boolean) {
        setOptionView(binding.tvPlaceResultFilterSave, binding.ivPlaceResultFilterSave, isActive)
    }

    private fun setFeedOptionView(isActive: Boolean) {
        setOptionView(binding.tvPlaceResultFilterFeed, binding.ivPlaceResultFilterFeed, isActive)
    }

    private fun setDistanceOptionView(isActive: Boolean) {
        setOptionView(
            binding.tvPlaceResultFilterDistance,
            binding.ivPlaceResultFilterDistance,
            isActive
        )
    }

    private fun setOptionView(textView: TextView, imageView: ImageView, isActive: Boolean) {
        imageView.visibility = when (isActive) {
            true -> View.VISIBLE
            else -> View.INVISIBLE
        }
    }

    private fun setOnClickListener() {
        binding.tvPlaceResultFilterSave.setOnClickListener {
            orderType = PlaceOrderType.SAVE
            setOrderTypeView()
        }
        binding.tvPlaceResultFilterFeed.setOnClickListener {
            orderType = PlaceOrderType.FEED
            setOrderTypeView()
        }
        binding.tvPlaceResultFilterDistance.setOnClickListener {
            orderType = PlaceOrderType.DISTANCE
            setOrderTypeView()
        }
        binding.tvPlaceResultFilterApply.setOnClickListener {
            orderTypeCallback?.onClick(orderType)
            dialog?.dismiss()
        }
    }
}
