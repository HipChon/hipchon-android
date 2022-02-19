package com.gritbus.hipchon.ui.save

import android.os.Bundle
import android.view.View
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentSaveBinding
import com.gritbus.hipchon.utils.BaseViewUtil

class SaveFragment : BaseViewUtil.BaseFragment<FragmentSaveBinding>(R.layout.fragment_save) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {}
}
