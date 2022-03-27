package com.gritbus.hipchon.ui.home.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.gritbus.hipchon.R
import com.gritbus.hipchon.data.model.feed.FeedBestAllDataItem
import com.gritbus.hipchon.data.model.place.PlaceHipSearchAllDataItem
import com.gritbus.hipchon.databinding.FragmentHomeBinding
import com.gritbus.hipchon.domain.model.Area
import com.gritbus.hipchon.domain.model.Hashtag
import com.gritbus.hipchon.domain.model.PlaceSearchFilterData
import com.gritbus.hipchon.domain.model.Type
import com.gritbus.hipchon.ui.feed.view.FeedDetailActivity
import com.gritbus.hipchon.ui.home.adapter.BannerViewPagerAdapter
import com.gritbus.hipchon.ui.home.adapter.BestFeedViewPagerAdapter
import com.gritbus.hipchon.ui.home.adapter.HomeLocalHipsterAdapter
import com.gritbus.hipchon.ui.home.adapter.WeeklyHipPlaceAdapter
import com.gritbus.hipchon.ui.home.viewmodel.HomeViewModel
import com.gritbus.hipchon.ui.place.view.PlaceDetailActivity
import com.gritbus.hipchon.ui.place.view.PlaceResultActivity
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.ItemDecorationWithHorizontalSpacing
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseViewUtil.BaseFragment<FragmentHomeBinding>(R.layout.fragment_home),
    BannerFragment.OnSwipeListener, BestFeedFragment.OnClickListener {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var localHipsterAdapter: HomeLocalHipsterAdapter
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

    override fun onSwipe(position: Int, imageCount: Int) {
        binding.tvHomeBanner.text = resources.getString(R.string.home_banner, position, imageCount)
    }

    private fun initData() {
        viewModel.getLocalHipsterAllData()
        viewModel.getBannerAllData()
        viewModel.getBestFeedAllData()
        viewModel.getWeeklyHipPlaceAllData()
    }

    private fun setItemDecoration() {
        binding.rvHomeLocalHipsterPick.addItemDecoration(
            ItemDecorationWithHorizontalSpacing(
                LOCAL_HIPSTER_ITEM_SPACING
            )
        )
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
            // TODO 해시태그로의 검색으로 수정 필요
            putExtra(
                HomeQuickSearchFragment.QUICK_SEARCH_FILTER,
                PlaceSearchFilterData(Area.ALL, Type.NOTHING)
            )
        })
    }

    private fun setAdapter() {
        localHipsterAdapter = HomeLocalHipsterAdapter(::localHipsterPickCallback)
        weeklyHipPlaceAdapter = WeeklyHipPlaceAdapter(::hipPlaceClickCallback, ::placeSaveCallback)

        binding.rvHomeLocalHipsterPick.adapter = localHipsterAdapter
        binding.rvHomeWeeklyHipPlace.adapter = weeklyHipPlaceAdapter
    }

    private fun localHipsterPickCallback() {
        startActivity(Intent(requireContext(), LocalHipsterPickActivity::class.java))
    }

    private fun hipPlaceClickCallback() {
        startActivity(Intent(requireContext(), PlaceDetailActivity::class.java))
    }

    private fun placeSaveCallback(weeklyHipPlaceData: PlaceHipSearchAllDataItem) {
        viewModel.updateSave(weeklyHipPlaceData)
    }

    private fun setObserver() {
        viewModel.localHipsterAllData.observe(viewLifecycleOwner) {
            localHipsterAdapter.submitList(it)
        }
        viewModel.bannerAllData.observe(viewLifecycleOwner) {
            setBanner(it)
        }
        viewModel.bestFeedAllData.observe(viewLifecycleOwner) {
            setBestFeed(it)
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

    private fun setBestFeed(bestFeedAllData: List<FeedBestAllDataItem>) {
        binding.vpHomeBestFeed.adapter =
            BestFeedViewPagerAdapter(
                bestFeedAllData,
                childFragmentManager,
                viewLifecycleOwner.lifecycle
            )
        binding.diHomeBestFeed.setViewPager2(binding.vpHomeBestFeed)
    }

    override fun onClick() {
        startActivity(Intent(requireContext(), FeedDetailActivity::class.java))
    }

    companion object {
        const val LOCAL_HIPSTER_ITEM_SPACING = 8
    }
}
