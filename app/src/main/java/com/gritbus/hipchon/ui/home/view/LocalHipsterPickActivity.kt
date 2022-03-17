package com.gritbus.hipchon.ui.home.view

import android.os.Bundle
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityLocalHipsterPickBinding
import com.gritbus.hipchon.ui.home.adapter.LocalHipsterAdapter
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.ItemDecorationWithStroke

class LocalHipsterPickActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityLocalHipsterPickBinding>(R.layout.activity_local_hipster_pick) {

    private lateinit var localHipsterAdapter: LocalHipsterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
        setAdapter()
        setOnClickListener()
    }

    private fun setAdapter() {
        localHipsterAdapter =
            LocalHipsterAdapter(
                supportFragmentManager,
                lifecycle
            )
        binding.rvLocalHipsterPick.adapter = localHipsterAdapter
        binding.rvLocalHipsterPick.addItemDecoration(ItemDecorationWithStroke(true))
        localHipsterAdapter.submitList(listOf(0, 1, 2, 3, 4, 5))
    }

    private fun setOnClickListener() {
        binding.mtLocalHipsterPick.setNavigationOnClickListener {
            finish()
        }
    }
}
