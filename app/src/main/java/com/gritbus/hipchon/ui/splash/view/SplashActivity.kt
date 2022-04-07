package com.gritbus.hipchon.ui.splash.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivitySplashBinding
import com.gritbus.hipchon.ui.MainActivity
import com.gritbus.hipchon.ui.onboard.view.OnboardingActivity
import com.gritbus.hipchon.ui.splash.viewmodel.SplashViewModel
import com.gritbus.hipchon.utils.BaseViewUtil
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
        viewModel.getAutoInfo()
        setObserver()
    }

    private fun setObserver() {
        viewModel.canLoginAuto.observe(this) {
            if (it) {
                viewModel.loginAuto()
            } else {
                startNextActivityWithHandling(Intent(baseContext, OnboardingActivity::class.java))
            }
        }
        viewModel.isLoginSuccess.observe(this) {
            if (it) {
                viewModel.getUserId()
            } else {
                startNextActivityWithHandling(Intent(baseContext, OnboardingActivity::class.java))
            }
        }
        viewModel.hasUserId.observe(this) {
            if (it) {
                startNextActivityWithHandling(Intent(baseContext, MainActivity::class.java))
            } else {
                startNextActivityWithHandling(Intent(baseContext, OnboardingActivity::class.java))
            }
        }
    }

    private fun startNextActivityWithHandling(intent: Intent) {
        Handler(Looper.getMainLooper())
            .postDelayed({
                startActivity(intent)
                finish()
            }, 2000)
    }
}
