package com.gritbus.hipchon.ui.review

import android.os.Bundle
import android.view.View
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentReviewBinding
import com.gritbus.hipchon.utils.BaseViewUtil

class ReviewFragment : BaseViewUtil.BaseFragment<FragmentReviewBinding>(R.layout.fragment_review) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {}
}
