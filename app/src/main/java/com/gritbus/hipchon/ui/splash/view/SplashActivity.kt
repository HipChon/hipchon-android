package com.gritbus.hipchon.ui.splash.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityMainBinding
import com.gritbus.hipchon.ui.MainActivity
import com.gritbus.hipchon.utils.BaseViewUtil

@SuppressLint("CustomSplashScreen")
class SplashActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityMainBinding>(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
        startNextActivityWithHandling(Intent(baseContext, MainActivity::class.java))
    }

    private fun startNextActivityWithHandling(intent: Intent) {
        Handler(Looper.getMainLooper())
            .postDelayed({
                startActivity(intent)
                finish()
            }, 2000)
    }
}
