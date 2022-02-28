package com.gritbus.hipchon.ui.place.view

import android.os.Bundle
import androidx.activity.viewModels
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityPlaceResultBinding
import com.gritbus.hipchon.ui.place.adapter.PlaceResultAdapter
import com.gritbus.hipchon.ui.place.viewmodel.PlaceResultViewModel
import com.gritbus.hipchon.utils.BaseViewUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceResultActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityPlaceResultBinding>(R.layout.activity_place_result) {

    private val viewModel: PlaceResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
        setObserver()
        setAdapter()
        setOnClickListener()
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
    }

    private fun setAdapter() {
        binding.rvPlaceResult.adapter = PlaceResultAdapter().apply {
            submitList(mutableListOf("test", "test", "test", "test", "test", "test", "test"))
        }
    }

    private fun setOnClickListener() {
        binding.mtPlaceResultTitle.setNavigationOnClickListener {
            finish()
        }
        binding.mtPlaceResultTitle.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.place_result_filter -> {
                    val placeResultFilterFragment = PlaceResultFilterFragment()
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
}
