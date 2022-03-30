package com.gritbus.hipchon.ui.onboard.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivitySignupBinding
import com.gritbus.hipchon.ui.MainActivity
import com.gritbus.hipchon.ui.onboard.viewmodel.SignupViewModel
import com.gritbus.hipchon.utils.BaseViewUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivitySignupBinding>(R.layout.activity_signup) {

    private val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.mtSignupTitle.setNavigationOnClickListener {
            if (supportFragmentManager.fragments.size == 1) {
                finish()
            } else {
                supportFragmentManager.popBackStack()
            }
        }
    }

    fun moveToNextLevel(baseFragment: Fragment, newFragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .hide(baseFragment)
            .add(binding.fcvSignup.id, newFragment)
            .addToBackStack(null)
            .commit()
    }

    fun moveToMain() {
        startActivity(Intent(baseContext, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }
}
