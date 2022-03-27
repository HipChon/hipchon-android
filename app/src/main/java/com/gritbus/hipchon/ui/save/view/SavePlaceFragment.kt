package com.gritbus.hipchon.ui.save.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentSavePlaceBinding
import com.gritbus.hipchon.ui.save.adapter.SavePlaceAdapter
import com.gritbus.hipchon.ui.save.viewmodel.SaveViewModel
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.ItemDecorationWithStroke
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavePlaceFragment : BaseViewUtil.BaseFragment<FragmentSavePlaceBinding>(R.layout.fragment_save_place) {

    private val viewModel: SaveViewModel by viewModels()
    private lateinit var savePlaceAdapter: SavePlaceAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        setAdapter()
        setObserver()
        viewModel.getMySavePlace()
    }

    private fun setAdapter() {
        savePlaceAdapter = SavePlaceAdapter()
        binding.rvSavePlace.adapter = savePlaceAdapter
        binding.rvSavePlace.addItemDecoration(ItemDecorationWithStroke(false))
    }

    private fun setObserver() {
        viewModel.savePlaceAllData.observe(viewLifecycleOwner) {
            savePlaceAdapter.submitList(it)
            if (it.isNullOrEmpty()){
                binding.llSavePlaceEmpty.visibility = View.VISIBLE
            } else {
                binding.llSavePlaceEmpty.visibility = View.INVISIBLE
            }
        }
    }

    companion object {
        const val CATEGORY_TITLE = "com.gritbus.hipchon.ui.save.view CATEGORY_TITLE"

        fun createSavePlaceFragment(
            title: String
        ): SavePlaceFragment {
            return SavePlaceFragment().apply {
                arguments = bundleOf(
                    CATEGORY_TITLE to title
                )
            }
        }
    }
}
