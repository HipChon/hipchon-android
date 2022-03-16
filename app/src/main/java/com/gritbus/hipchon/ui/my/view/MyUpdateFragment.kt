package com.gritbus.hipchon.ui.my.view

import android.os.Bundle
import android.view.View
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentMyUpdateBinding
import com.gritbus.hipchon.utils.BaseViewUtil

class MyUpdateFragment :
    BaseViewUtil.BaseFragment<FragmentMyUpdateBinding>(R.layout.fragment_my_update) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
    }
}
