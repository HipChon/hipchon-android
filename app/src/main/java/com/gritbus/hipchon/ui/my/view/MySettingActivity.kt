package com.gritbus.hipchon.ui.my.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityMySettingBinding
import com.gritbus.hipchon.ui.my.viewmodel.MySettingViewModel
import com.gritbus.hipchon.ui.onboard.view.OnboardingActivity
import com.gritbus.hipchon.utils.BaseViewUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MySettingActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityMySettingBinding>(R.layout.activity_my_setting) {

    private val viewModel: MySettingViewModel by viewModels()
    private val suggestUrl = "https://bit.ly/3qRHltE"
    private val noticeUrl = "https://bit.ly/3uJWD4v"
    private val faqUrl = "https://bit.ly/3LrIjV3"
    private val centerUrl = "http://pf.kakao.com/_xgHYNb"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
        setObserver()
        setOnClickListener()
    }

    private fun setObserver() {
        viewModel.isLogoutSuccess.observe(this) {
            if (it) {
                startActivity(Intent(baseContext, OnboardingActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
            }
        }
        viewModel.isLeaveSuccess.observe(this) {
            if (it) {
                startActivity(Intent(baseContext, OnboardingActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
            }
        }
    }

    private fun setOnClickListener() {
        binding.mtMySetting.setOnClickListener {
            finish()
        }
        binding.tvMySettingAppAccount.setOnClickListener {
            startActivity(Intent(baseContext, MySettingInfoActivity::class.java))
        }
        binding.tvMySettingAppLogout.setOnClickListener {
            viewModel.logoutUser()
        }
        binding.tvMySettingAppLeave.setOnClickListener {
            viewModel.leaveUser()
        }
        binding.tvMySettingHostSuggest.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(suggestUrl)))
        }
        binding.tvMySettingCenterNotice.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(noticeUrl)))
        }
        binding.tvMySettingCenterQna.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(faqUrl)))
        }
        binding.tvMySettingCenterQuestion.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(centerUrl)))
        }
    }
}
