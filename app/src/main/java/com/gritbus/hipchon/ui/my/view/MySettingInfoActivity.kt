package com.gritbus.hipchon.ui.my.view

import android.os.Bundle
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityMySettingInfoBinding
import com.gritbus.hipchon.utils.BaseViewUtil

class MySettingInfoActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityMySettingInfoBinding>(R.layout.activity_my_setting_info) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
    }
}
