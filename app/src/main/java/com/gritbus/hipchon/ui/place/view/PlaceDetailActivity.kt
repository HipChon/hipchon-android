package com.gritbus.hipchon.ui.place.view

import android.os.Bundle
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityPlaceDetailBinding
import com.gritbus.hipchon.utils.BaseViewUtil

class PlaceDetailActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityPlaceDetailBinding>(R.layout.activity_place_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_detail)
    }

    override fun initView() {}
}