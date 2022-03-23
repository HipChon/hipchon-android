package com.gritbus.hipchon.ui.place.view

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityPlaceDetailBinding
import com.gritbus.hipchon.databinding.ItemPlaceDetailKeywordBinding
import com.gritbus.hipchon.domain.model.KeywordFacility
import com.gritbus.hipchon.domain.model.KeywordMood
import com.gritbus.hipchon.domain.model.KeywordSatisfaction
import com.gritbus.hipchon.ui.feed.adapter.FeedAdapter
import com.gritbus.hipchon.ui.feed.view.FeedDetailActivity
import com.gritbus.hipchon.ui.place.adapter.PlaceDetailThumbAdapter
import com.gritbus.hipchon.ui.place.adapter.PlaceMenuAdapter
import com.gritbus.hipchon.ui.place.viewmodel.PlaceDetailViewModel
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.ItemDecorationWithStroke
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceDetailActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityPlaceDetailBinding>(R.layout.activity_place_detail),
    OnMapReadyCallback {

    private val viewModel: PlaceDetailViewModel by viewModels()
    private lateinit var menuAdapter: PlaceMenuAdapter
    private lateinit var reviewAdapter: FeedAdapter
    private lateinit var naverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mapFragment =
            (supportFragmentManager.findFragmentById(R.id.view_place_detail_map_preview) as MapFragment?
                ?: MapFragment.newInstance().also {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.view_place_detail_map_preview, it).commit()
                })
        mapFragment.getMapAsync(this)
        initView()
    }

    override fun initView() {
        setMenuAdapter()
        setReviewAdapter()
        initData()
        setMap()
        setOnClickListener()
        setObserver()
    }

    private fun setMenuAdapter() {
        menuAdapter = PlaceMenuAdapter()
        binding.rvPlaceDetailMenu.adapter = menuAdapter
    }

    private fun setReviewAdapter() {
        reviewAdapter = FeedAdapter(true, ::moveToFeedDetail)
        binding.rvPlaceDetailReview.adapter = reviewAdapter
        binding.rvPlaceDetailReview.addItemDecoration(ItemDecorationWithStroke(false))
    }

    private fun moveToFeedDetail() {
        startActivity(Intent(baseContext, FeedDetailActivity::class.java))
    }

    private fun initData() {
        viewModel.initData()
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.uiSettings.isZoomControlEnabled = false
        Marker().apply {
            position = LatLng(37.5670135, 126.9783740)
            map = naverMap
            icon = OverlayImage.fromResource(R.drawable.ic_map_marker)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setMap() {
        binding.ivPlaceDetailMapPreview.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.nsvPlaceDetail.requestDisallowInterceptTouchEvent(true)
                    false
                }
                MotionEvent.ACTION_UP -> {
                    binding.nsvPlaceDetail.requestDisallowInterceptTouchEvent(false)
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    binding.nsvPlaceDetail.requestDisallowInterceptTouchEvent(true)
                    false
                }
                else -> {
                    true
                }
            }
        }
    }

    private fun setOnClickListener() {
        binding.mtPlaceDetail.setNavigationOnClickListener {
            finish()
        }
        binding.ivPlaceDetailCall.setOnClickListener {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:01012345678")))
        }
        binding.ivPlaceDetailShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        binding.ivPlaceDetailFeed.setOnClickListener {
            binding.nsvPlaceDetail.smoothScrollTo(0, binding.ivPlaceDetailReview.y.toInt())
        }
        binding.ivPlaceDetailSave.setOnClickListener {
            viewModel.setSave()
        }
        binding.acbPlaceDetailLink.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.naver.com")))
        }
        binding.tvPlaceDetailMapCopy.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("주소", binding.tvPlaceDetailMapCopyAddress.text)
            clipboard.setPrimaryClip(clip)
        }
    }

    private fun setObserver() {
        viewModel.thumbImages.observe(this) {
            setThumbAdapter(it)
        }
        viewModel.isSave.observe(this) {
            updateSaveView(it)
        }
        viewModel.menuAllData.observe(this) {
            menuAdapter.submitList(it)
        }
        viewModel.keyword.observe(this) {
            it.forEach { keyword ->
                val keywordView = layoutInflater.inflate(
                    R.layout.item_place_detail_keyword,
                    binding.llPlaceDetailKeyword,
                    false
                ) as ConstraintLayout

                val keywordBinding =
                    DataBindingUtil.bind<ItemPlaceDetailKeywordBinding>(keywordView)
                if (keyword != null) {
                    getKeywordData(keyword)?.let { iconAndContentAndColor ->
                        keywordBinding?.ivPlaceDetailKeywordIcon?.background =
                            ContextCompat.getDrawable(baseContext, iconAndContentAndColor.first)
                        keywordBinding?.tvPlaceDetailKeywordContent?.text =
                            resources.getString(iconAndContentAndColor.second)
                        keywordBinding?.root?.backgroundTintList = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                baseContext,
                                iconAndContentAndColor.third
                            )
                        )
                    }
                    binding.llPlaceDetailKeyword.addView(keywordView)
                }
            }
        }
        viewModel.reviewPreview.observe(this) {
            reviewAdapter.submitList(it)
        }
    }

    private fun getKeywordData(keyword: Any): Triple<Int, Int, Int>? {
        return when (keyword) {
            is KeywordFacility -> {
                Triple(
                    keyword.iconDrawable,
                    keyword.contentString,
                    R.color.secondary_yellow
                )
            }
            is KeywordMood -> {
                Triple(
                    keyword.iconDrawable,
                    keyword.contentString,
                    R.color.primary_green
                )
            }
            is KeywordSatisfaction -> {
                Triple(
                    keyword.iconDrawable,
                    keyword.contentString,
                    R.color.secondary_blue
                )
            }
            else -> {
                null
            }
        }
    }

    private fun setThumbAdapter(imageList: List<String>) {
        binding.vpPlaceDetailThumb.adapter = PlaceDetailThumbAdapter(
            imageList, supportFragmentManager, lifecycle
        )
        setTabLayout()
    }

    private fun setTabLayout() {
        TabLayoutMediator(
            binding.tlPlaceDetailThumb,
            binding.vpPlaceDetailThumb
        ) { _, _ -> }.attach()
    }

    private fun updateSaveView(isSave: Boolean) {
        when (isSave) {
            true -> {
                binding.ivPlaceDetailSave.setImageResource(R.drawable.ic_save_filled)
                binding.ivPlaceDetailSave.imageTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(baseContext, R.color.primary_green)
                    )
            }
            false -> {
                binding.ivPlaceDetailSave.setImageResource(R.drawable.ic_save)
                binding.ivPlaceDetailSave.imageTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(baseContext, R.color.black))
            }
        }
    }
}
