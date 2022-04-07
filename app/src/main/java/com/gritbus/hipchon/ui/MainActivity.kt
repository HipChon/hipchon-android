package com.gritbus.hipchon.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityMainBinding
import com.gritbus.hipchon.ui.feed.view.FeedFragment
import com.gritbus.hipchon.ui.home.view.HomeFragment
import com.gritbus.hipchon.ui.my.view.MyFragment
import com.gritbus.hipchon.ui.save.view.SaveFragment
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.addFragment
import com.gritbus.hipchon.utils.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityMainBinding>(R.layout.activity_main) {

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
                    replaceFragment(binding.fcvMain.id, HomeFragment())
                    true
                }
                R.id.bottom_nav_feed -> {
                    replaceFragment(binding.fcvMain.id, FeedFragment())
                    true
                }
                R.id.bottom_nav_save -> {
                    replaceFragment(binding.fcvMain.id, SaveFragment())
                    true
                }
                R.id.bottom_nav_my -> {
                    replaceFragment(binding.fcvMain.id, MyFragment())
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun initHomeFragment() {
        replaceFragment(binding.fcvMain.id, HomeFragment())
    }

    fun addFragment(fragment: Fragment){
        addFragment(binding.fcvMain.id, fragment)
    }
}
