package com.gritbus.hipchon.ui.home

import android.os.Bundle
import android.view.View
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentHomeQuickSearchBinding
import com.gritbus.hipchon.utils.BaseViewUtil

class HomeQuickSearchFragment :
    BaseViewUtil.BaseBottomSheetDialogFragment<FragmentHomeQuickSearchBinding>(R.layout.fragment_home_quick_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {}
}
