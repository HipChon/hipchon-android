package com.gritbus.hipchon.ui.my.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityMySettingInfoBinding
import com.gritbus.hipchon.ui.my.viewmodel.MyInfoViewModel
import com.gritbus.hipchon.utils.BaseViewUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MySettingInfoActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityMySettingInfoBinding>(R.layout.activity_my_setting_info) {

    private val viewModel: MyInfoViewModel by viewModels()
    private val serviceTerm = "https://frost-kite-c1c.notion.site/156ae780da1d482f92ba93a852e83a27"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
        setClickListener()
        setObserver()
        viewModel.getUserProfile()
    }

    private fun setClickListener() {
        binding.mtMySettingInfo.setOnClickListener {
            finish()
        }
        binding.tvMySettingInfoTermsService.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(serviceTerm)))
        }
    }

    private fun setObserver() {
        viewModel.userProfile.observe(this) {
            binding.tvMySettingInfoEmailData.text = it.email
        }
    }
}
