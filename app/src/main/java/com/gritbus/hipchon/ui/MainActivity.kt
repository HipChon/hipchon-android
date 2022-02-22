package com.gritbus.hipchon.ui

import android.os.Bundle
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityMainBinding
import com.gritbus.hipchon.ui.home.HomeFragment
import com.gritbus.hipchon.ui.my.MyFragment
import com.gritbus.hipchon.ui.review.ReviewFragment
import com.gritbus.hipchon.ui.save.SaveFragment
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.replaceFragment

class MainActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val homeFragment: HomeFragment by lazy { HomeFragment() }
    private val reviewFragment: ReviewFragment by lazy { ReviewFragment() }
    private val saveFragment: SaveFragment by lazy { SaveFragment() }
    private val myFragment: MyFragment by lazy { MyFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
        setBottomNavigation()
    }

    private fun setBottomNavigation() {
        initHomeFragment()

        binding.bnvMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_nav_home -> {
                    replaceFragment(binding.fcvMain.id, homeFragment)
                    true
                }
                R.id.bottom_nav_review -> {
                    replaceFragment(binding.fcvMain.id, reviewFragment)
                    true
                }
                R.id.bottom_nav_save -> {
                    replaceFragment(binding.fcvMain.id, saveFragment)
                    true
                }
                R.id.bottom_nav_my -> {
                    replaceFragment(binding.fcvMain.id, myFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun initHomeFragment() {
        replaceFragment(binding.fcvMain.id, homeFragment)
    }
}