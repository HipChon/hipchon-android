package com.gritbus.hipchon.ui.place.view

import android.os.Bundle
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityPlaceResultBinding
import com.gritbus.hipchon.ui.place.adapter.PlaceResultAdapter
import com.gritbus.hipchon.utils.BaseViewUtil

class PlaceResultActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityPlaceResultBinding>(R.layout.activity_place_result) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
        setAdapter()
        setOnClickListener()
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
