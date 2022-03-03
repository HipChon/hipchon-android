package com.gritbus.hipchon.ui.home.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentHomeBinding
import com.gritbus.hipchon.domain.model.Hashtag
import com.gritbus.hipchon.domain.model.PlaceSearchFilterData
import com.gritbus.hipchon.domain.model.WeeklyHipPlaceData
import com.gritbus.hipchon.ui.home.adapter.BannerViewPagerAdapter
import com.gritbus.hipchon.ui.home.adapter.LocalHipsterAdapter
import com.gritbus.hipchon.ui.home.adapter.WeeklyHipPlaceAdapter
import com.gritbus.hipchon.ui.home.viewmodel.HomeViewModel
import com.gritbus.hipchon.ui.place.view.PlaceResultActivity
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

    override fun onResume() {
        super.onResume()
        initData()
    }

    private fun initData() {
        viewModel.getLocalHipsterAllData()
        viewModel.getBannerAllData()
        viewModel.getWeeklyHipPlaceAllData()
    }

    private fun setItemDecoration() {
        binding.rvHomeLocalHipsterPick.addItemDecoration(ItemDecoration(LOCAL_HIPSTER_ITEM_SPACING))
    }

    private fun setOnClickListener() {
        binding.tvHomeSearchBar.setOnClickListener {
            startQuickSearch()
        }
        binding.ivHomeHashtagFire.setOnClickListener {
            quickHashtagSearch(Hashtag.FIRE)
        }
        binding.ivHomeHashtagWater.setOnClickListener {
            quickHashtagSearch(Hashtag.WATER)
        }
        binding.ivHomeHashtagField.setOnClickListener {
            quickHashtagSearch(Hashtag.FIELD)
        }
        binding.ivHomeHashtagVacation.setOnClickListener {
            quickHashtagSearch(Hashtag.VACATION)
        }
    }

    private fun startQuickSearch() {
        val homeQuickSearchFragment = HomeQuickSearchFragment()
        homeQuickSearchFragment.show(parentFragmentManager, homeQuickSearchFragment.tag)
    }

    private fun quickHashtagSearch(hashtag: Hashtag) {
        startActivity(Intent(requireContext(), PlaceResultActivity::class.java).apply {
            putExtra(
                HomeQuickSearchFragment.QUICK_SEARCH_FILTER,
                PlaceSearchFilterData(hashtag = hashtag)
            )
        })
    }

    private fun setAdapter() {
        localHipsterAdapter = LocalHipsterAdapter()
        weeklyHipPlaceAdapter = WeeklyHipPlaceAdapter(::placeSaveCallback)

        binding.rvHomeLocalHipsterPick.adapter = localHipsterAdapter
        binding.rvHomeWeeklyHipPlace.adapter = weeklyHipPlaceAdapter
    }

    private fun placeSaveCallback(weeklyHipPlaceData: WeeklyHipPlaceData) {
        viewModel.updateSave(weeklyHipPlaceData)
    }

    private fun setObserver() {
        viewModel.localHipsterAllData.observe(viewLifecycleOwner) {
            localHipsterAdapter.submitList(it)
        }
        viewModel.bannerAllData.observe(viewLifecycleOwner) {
            setBanner(it)
        }
        viewModel.weeklyHipPlaceAllData.observe(viewLifecycleOwner) {
            weeklyHipPlaceAdapter.submitList(it)
        }
    }

    private fun setBanner(bannerAllData: List<String>) {
        binding.vpHomeBanner.adapter = BannerViewPagerAdapter(
            bannerAllData,
            childFragmentManager,
            viewLifecycleOwner.lifecycle
        )
    }

    companion object {
        const val LOCAL_HIPSTER_ITEM_SPACING = 8
    }
}
