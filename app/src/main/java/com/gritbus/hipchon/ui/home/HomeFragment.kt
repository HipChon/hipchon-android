package com.gritbus.hipchon.ui.home

import android.os.Bundle
import android.view.View
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentHomeBinding
import com.gritbus.hipchon.utils.BaseViewUtil

class HomeFragment : BaseViewUtil.BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {}
}
