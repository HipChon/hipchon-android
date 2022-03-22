package com.gritbus.hipchon.ui.place.view

import android.content.Intent
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
                it.area.value,
                it.type.value
            )
        }
        viewModel.placeAllData.observe(this) {
            placeResultAdapter.submitList(it)
        }
    }

    private fun setAdapter() {
        placeResultAdapter = PlaceResultAdapter(::placeDetailCallback, ::placeSaveCallback)
        binding.rvPlaceResult.adapter = placeResultAdapter
    }

    private fun placeSaveCallback(selectedPlaceData: PlaceData) {
        viewModel.updateSave(selectedPlaceData)
    }

    private fun placeDetailCallback(selectedPlaceData: PlaceData) {
        startActivity(Intent(baseContext, PlaceDetailActivity::class.java))
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
        binding.tvPlaceResult.setOnClickListener {
            val placeQuickSearchFragment = PlaceQuickSearchFragment().apply {
                arguments = bundleOf(PLACE_QUICK_SEARCH to viewModel.getSearchFilter())
            }
            placeQuickSearchFragment.show(
                supportFragmentManager,
                placeQuickSearchFragment.tag
            )
        }
    }

    override fun onClick(orderType: PlaceOrderType) {
        viewModel.setOrderType(orderType)
    }

    companion object {
        const val PLACE_ORDER_TYPE = "com.gritbus.hipchon.ui.place.view PLACE_ORDER_TYPE"
        const val PLACE_QUICK_SEARCH = "com.gritbus.hipchon.ui.place.view PLACE_QUICK_SEARCH"
    }
}
