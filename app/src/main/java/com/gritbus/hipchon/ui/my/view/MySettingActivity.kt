package com.gritbus.hipchon.ui.my.view

import android.os.Bundle
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityMySettingBinding
import com.gritbus.hipchon.utils.BaseViewUtil

class MySettingActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityMySettingBinding>(R.layout.activity_my_setting) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {}
}
