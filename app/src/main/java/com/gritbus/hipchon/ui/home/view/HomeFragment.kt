package com.gritbus.hipchon.ui.home.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentHomeBinding
import com.gritbus.hipchon.ui.home.adapter.LocalHipsterAdapter
import com.gritbus.hipchon.ui.home.adapter.WeeklyHipPlaceAdapter
import com.gritbus.hipchon.ui.home.viewmodel.HomeViewModel
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseViewUtil.BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var localHipsterAdapter: LocalHipsterAdapter
    private lateinit var weeklyHipPlaceAdapter: WeeklyHipPlaceAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        setItemDecoration()
        setOnClickListener()
        setAdapter()
        setObserver()
    }

    private fun setItemDecoration() {
        binding.rvHomeLocalHipsterPick.addItemDecoration(ItemDecoration(LOCAL_HIPSTER_ITEM_SPACING))
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    private fun setOnClickListener() {
        binding.tvHomeSearchBar.setOnClickListener {
            startQuickSearch()
        }
    }

    private fun startQuickSearch() {
        val homeQuickSearchFragment = HomeQuickSearchFragment()
        homeQuickSearchFragment.show(parentFragmentManager, homeQuickSearchFragment.tag)
    }

    private fun initData() {
        viewModel.getLocalHipsterAllData()
        viewModel.getWeeklyHipPlaceAllData()
    }

    private fun setAdapter() {
        localHipsterAdapter = LocalHipsterAdapter()
        weeklyHipPlaceAdapter = WeeklyHipPlaceAdapter()

        binding.rvHomeLocalHipsterPick.adapter = localHipsterAdapter
        binding.rvHomeWeeklyHipPlace.adapter = weeklyHipPlaceAdapter
    }

    private fun setObserver() {
        viewModel.localHipsterAllData.observe(viewLifecycleOwner) {
            localHipsterAdapter.submitList(it)
        }
        viewModel.weeklyHipPlaceAllData.observe(viewLifecycleOwner) {
            weeklyHipPlaceAdapter.submitList(it)
        }
    }

    companion object {
        const val LOCAL_HIPSTER_ITEM_SPACING = 8
    }
}
