package com.gritbus.hipchon.ui.home.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentHomeQuickSearchBinding
import com.gritbus.hipchon.domain.model.Area
import com.gritbus.hipchon.domain.model.Type
import com.gritbus.hipchon.ui.home.viewmodel.HomeQuickSearchViewModel
import com.gritbus.hipchon.ui.place.view.PlaceResultActivity
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
        setAreaChipGroup()
        setTypeChipGroup()
        setOnClickListener()
        setObserver()
    }

    private fun setAreaChipGroup() {
        enumValues<Area>().mapNotNull { if (it == Area.ALL) null else it }.forEach {
            val chip = layoutInflater.inflate(
                R.layout.item_filter_chip,
                binding.cgHomeQuickSearchArea,
                false
            ) as Chip
            chip.text = it.value

            binding.cgHomeQuickSearchArea.addView(chip)
        }
    }

    private fun setTypeChipGroup() {
        enumValues<Type>().mapNotNull { if (it == Type.NOTHING) null else it }.forEach {
            val chip = layoutInflater.inflate(
                R.layout.item_filter_chip,
                binding.cgHomeQuickSearchType,
                false
            ) as Chip
            chip.text = it.value

            binding.cgHomeQuickSearchType.addView(chip)
        }
    }

    private fun setOnClickListener() {
        binding.ivHomeQuickSearchClose.setOnClickListener {
            dialog?.dismiss()
        }
        binding.cgHomeQuickSearchArea.setOnCheckedChangeListener { group, checkedId ->
            viewModel.setArea(group.findViewById<Chip>(checkedId).text.toString())
        }
        binding.cgHomeQuickSearchType.setOnCheckedChangeListener { group, checkedId ->
            viewModel.setType(group.findViewById<Chip>(checkedId).text.toString())
        }
        binding.tvHomeQuickSearchReset.setOnClickListener {
            resetAllFilter()
        }
        binding.tvHomeQuickSearchApply.setOnClickListener {
            viewModel.getSearchFilter()?.let { filterData ->
                startActivity(Intent(requireContext(), PlaceResultActivity::class.java).apply {
                    putExtra(QUICK_SEARCH_FILTER, filterData)
                })
            }
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

    companion object {
        const val QUICK_SEARCH_FILTER = "com.gritbus.hipchon.ui.home.view"
    }
}
