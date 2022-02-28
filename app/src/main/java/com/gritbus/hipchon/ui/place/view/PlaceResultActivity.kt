package com.gritbus.hipchon.ui.place.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityPlaceResultBinding
import com.gritbus.hipchon.domain.model.PlaceData
import com.gritbus.hipchon.domain.model.PlaceOrderType
import com.gritbus.hipchon.ui.place.adapter.PlaceResultAdapter
import com.gritbus.hipchon.ui.place.viewmodel.PlaceResultViewModel
import com.gritbus.hipchon.utils.BaseViewUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceResultActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityPlaceResultBinding>(R.layout.activity_place_result),
    PlaceResultFilterFragment.OnOrderTypeClickListener {

    private val viewModel: PlaceResultViewModel by viewModels()
    private lateinit var placeResultAdapter: PlaceResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
        setObserver()
        setAdapter()
        setOnClickListener()
        viewModel.getPlaceData()
    }

    private fun setObserver() {
        viewModel.placeSearchFilterData.observe(this) {
            binding.tvPlaceResult.text = resources.getString(
                R.string.place_result_filter_title,
                "${it.personCount}명",
                it.area.value,
                it.hashtag.value
            )
        }
        viewModel.placeAllData.observe(this) {
            placeResultAdapter.submitList(it)
        }
    }

    private fun setAdapter() {
        placeResultAdapter = PlaceResultAdapter(::placeSaveCallback)
        binding.rvPlaceResult.adapter = placeResultAdapter
    }

    private fun placeSaveCallback(selectedPlaceData: PlaceData) {
        viewModel.updateSave(selectedPlaceData)
    }

    private fun setOnClickListener() {
        binding.mtPlaceResultTitle.setNavigationOnClickListener {
            finish()
        }
        binding.mtPlaceResultTitle.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.place_result_filter -> {
                    val placeResultFilterFragment = PlaceResultFilterFragment().apply {
                        arguments = bundleOf(PLACE_ORDER_TYPE to viewModel.placeOrderType.value)
                    }
                    placeResultFilterFragment.show(
                        supportFragmentManager,
                        placeResultFilterFragment.tag
                    )
                    true
                }
                else -> false
            }
        }
    }

    override fun onClick(orderType: PlaceOrderType) {
        viewModel.setOrderType(orderType)
    }

    companion object {
        const val PLACE_ORDER_TYPE = "com.gritbus.hipchon.ui.place.view"
    }
}
