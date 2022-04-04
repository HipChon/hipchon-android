package com.gritbus.hipchon.ui.home.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.gritbus.hipchon.R
import com.gritbus.hipchon.data.model.event.EventAllDataItem
import com.gritbus.hipchon.data.model.feed.FeedBestAllDataItem
import com.gritbus.hipchon.data.model.place.PlaceHipSearchAllDataItem
import com.gritbus.hipchon.databinding.FragmentHomeBinding
import com.gritbus.hipchon.domain.model.Hashtag
import com.gritbus.hipchon.ui.feed.view.FeedDetailActivity
import com.gritbus.hipchon.ui.feed.view.FeedFragment
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
    private val kakaoCounseling = "http://pf.kakao.com/_xgHYNb/chat"
    private val registerUrl = "https://pf.kakao.com/_xgHYNb"
    private val serviceTerm = "https://frost-kite-c1c.notion.site/156ae780da1d482f92ba93a852e83a27"
    private val personalTerm = "https://frost-kite-c1c.notion.site/f6239a9d67784836b69cc4bedfc95a7e"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        setItemDecoration()
        setOnClickListener()
        setAdapter()
        setObserver()
        initData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWeeklyHipPlaceAllData()
    }

    override fun onSwipe(position: Int, imageCount: Int) {
        binding.tvHomeBanner.text = resources.getString(R.string.home_banner, position, imageCount)
    }

    private fun initData() {
        viewModel.getLocalHipsterAllData()
        viewModel.getBannerAllData()
        viewModel.getBestFeedAllData()
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
        binding.mbHomeKakaoCounseling.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(kakaoCounseling)))
        }
        binding.tvHomePlaceRegister.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(registerUrl)))
        }
        binding.tvHomeTerms.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(serviceTerm)))
        }
        binding.tvHomePlacePrivacy.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(personalTerm)))
        }
    }

    private fun startQuickSearch() {
        val homeQuickSearchFragment = HomeQuickSearchFragment()
        homeQuickSearchFragment.show(parentFragmentManager, homeQuickSearchFragment.tag)
    }

    private fun quickHashtagSearch(hashtag: Hashtag) {
        startActivity(Intent(requireContext(), PlaceResultActivity::class.java).apply {
            putExtra(
                HASHTAG_SEARCH,
                hashtag
            )
        })
    }

    private fun setAdapter() {
        localHipsterAdapter = HomeLocalHipsterAdapter(::localHipsterPickCallback)
        weeklyHipPlaceAdapter = WeeklyHipPlaceAdapter(::hipPlaceClickCallback, ::placeSaveCallback)

        binding.rvHomeLocalHipsterPick.adapter = localHipsterAdapter
        binding.rvHomeWeeklyHipPlace.adapter = weeklyHipPlaceAdapter
    }

    private fun localHipsterPickCallback(localHipsterId: Int) {
        startActivity(Intent(requireContext(), LocalHipsterPickActivity::class.java).apply {
            putExtra(LOCAL_HIPSTER_ID, localHipsterId)
        })
    }

    private fun hipPlaceClickCallback(selectedPlaceId: Int) {
        startActivity(Intent(requireContext(), PlaceDetailActivity::class.java).apply {
            putExtra(PlaceResultActivity.PLACE_ID, selectedPlaceId)
        })
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
        viewModel.currentBestFeedData.observe(viewLifecycleOwner) {
            startActivity(Intent(requireContext(), FeedDetailActivity::class.java).apply {
                putExtra(FeedFragment.FEED_DETAIL_DATA, it)
            })
        }
    }

    private fun setBanner(bannerAllData: List<EventAllDataItem>) {
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

    override fun onClick(postId: Int) {
        viewModel.getCurrentBestFeedData(postId)
    }

    companion object {
        const val LOCAL_HIPSTER_ITEM_SPACING = 8
        const val LOCAL_HIPSTER_ID = "com.gritbus.hipchon.ui.home.view LOCAL_HIPSTER_ID"
        const val HASHTAG_SEARCH = "com.gritbus.hipchon.ui.home.view HASHTAG_SEARCH"
    }
}
