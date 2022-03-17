package com.gritbus.hipchon.ui.save.view

import android.os.Bundle
import android.view.View
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentSavePlaceBinding
import com.gritbus.hipchon.ui.save.adapter.SavePlaceAdapter
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.ItemDecorationWithStroke

class SavePlaceFragment : BaseViewUtil.BaseFragment<FragmentSavePlaceBinding>(R.layout.fragment_save_place) {

    private lateinit var savePlaceAdapter: SavePlaceAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        setAdapter()
    }

    private fun setAdapter() {
        savePlaceAdapter = SavePlaceAdapter()
        binding.rvSavePlace.adapter = savePlaceAdapter
        binding.rvSavePlace.addItemDecoration(ItemDecorationWithStroke())
        savePlaceAdapter.submitList(listOf(1, 2, 3, 4, 5))
    }
}
