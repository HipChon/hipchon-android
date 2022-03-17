package com.gritbus.hipchon.ui.save.view

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentSaveBinding
import com.gritbus.hipchon.ui.save.adapter.SaveAdapter
import com.gritbus.hipchon.utils.BaseViewUtil

class SaveFragment : BaseViewUtil.BaseFragment<FragmentSaveBinding>(R.layout.fragment_save) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        setAdapter()
        setTabLayout()
    }

    private fun setAdapter() {
        binding.vpSave.adapter = SaveAdapter(this)
    }

    private fun setTabLayout() {
        val titleList = listOf("전체", "카페", "미식", "활동", "자연")
        TabLayoutMediator(binding.tlSave, binding.vpSave) { tab, position ->
            tab.text = titleList[position]
        }.attach()
    }
}
