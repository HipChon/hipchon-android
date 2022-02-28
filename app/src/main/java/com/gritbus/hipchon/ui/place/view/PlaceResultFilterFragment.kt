package com.gritbus.hipchon.ui.place.view

import android.os.Bundle
import android.view.View
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentPlaceResultFilterBinding
import com.gritbus.hipchon.utils.BaseViewUtil


class PlaceResultFilterFragment :
    BaseViewUtil.BaseBottomSheetDialogFragment<FragmentPlaceResultFilterBinding>(R.layout.fragment_place_result_filter) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {}
}
