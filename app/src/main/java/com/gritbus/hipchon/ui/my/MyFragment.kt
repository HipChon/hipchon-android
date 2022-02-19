package com.gritbus.hipchon.ui.my

import android.os.Bundle
import android.view.View
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentMyBinding
import com.gritbus.hipchon.utils.BaseViewUtil

class MyFragment : BaseViewUtil.BaseFragment<FragmentMyBinding>(R.layout.fragment_my) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {}
}
