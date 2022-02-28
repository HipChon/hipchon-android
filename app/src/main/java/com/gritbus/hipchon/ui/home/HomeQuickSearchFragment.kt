package com.gritbus.hipchon.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentHomeQuickSearchBinding
import com.gritbus.hipchon.utils.BaseViewUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeQuickSearchFragment :
    BaseViewUtil.BaseBottomSheetDialogFragment<FragmentHomeQuickSearchBinding>(R.layout.fragment_home_quick_search) {

    private val viewModel: HomeQuickSearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setOnClickListener()
        setObserver()
    }

    private fun setOnClickListener() {
        binding.ivHomeQuickSearchClose.setOnClickListener {
            dialog?.dismiss()
        }
        binding.tvHomeQuickSearchReset.setOnClickListener {
            resetAllFilter()
        }
        binding.tvHomeQuickSearchApply.setOnClickListener {
            // TODO 적용누르면 바로 공간리스트 검색결과 페이지로 이동
            dialog?.dismiss()
        }
    }

    private fun setObserver() {
        viewModel.isFilterChange.observe(viewLifecycleOwner) {
            setApplyButton(it)
        }
    }

    private fun setApplyButton(isActive: Boolean) {
        val backgroundDrawable = when (isActive) {
            true -> R.drawable.bg_quick_search_apply_active
            else -> R.drawable.bg_quick_search_apply_default
        }

        val textColor = when (isActive) {
            true -> R.color.white
            else -> R.color.black
        }

        binding.tvHomeQuickSearchApply.background =
            ContextCompat.getDrawable(requireContext(), backgroundDrawable)
        binding.tvHomeQuickSearchApply.setTextColor(
            ContextCompat.getColor(requireContext(), textColor)
        )
    }

    private fun resetAllFilter() {
        viewModel.resetAllFilter()
    }
}
