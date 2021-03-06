package com.gritbus.hipchon.ui.place.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.gritbus.hipchon.R
import com.gritbus.hipchon.data.model.feed.FeedAllDataItem
import com.gritbus.hipchon.data.model.feed.FeedPlaceItem
import com.gritbus.hipchon.databinding.ActivityPlaceDetailBinding
import com.gritbus.hipchon.databinding.ItemPlaceDetailKeywordBinding
import com.gritbus.hipchon.domain.model.KeywordFacility
import com.gritbus.hipchon.domain.model.KeywordMood
import com.gritbus.hipchon.domain.model.KeywordSatisfaction
import com.gritbus.hipchon.ui.feed.adapter.FeedAdapter
import com.gritbus.hipchon.ui.feed.view.FeedCreateActivity
import com.gritbus.hipchon.ui.feed.view.FeedDetailActivity
import com.gritbus.hipchon.ui.feed.view.FeedFragment.Companion.FEED_DETAIL_DATA
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
        setMap()
        setOnClickListener()
        setObserver()
    }

    override fun onResume() {
        super.onResume()
        if (this::naverMap.isInitialized){
            binding.llPlaceDetailKeyword.removeAllViews()
            initData()
        }
    }

    private fun setMenuAdapter() {
        menuAdapter = PlaceMenuAdapter()
        binding.rvPlaceDetailMenu.adapter = menuAdapter
    }

    private fun setReviewAdapter() {
        reviewAdapter =
            FeedAdapter(true, ::moveToFeedDetail, ::moveToPlaceDetail, ::likePost, ::savePlace, ::reportPost, ::reportUser)
        binding.rvPlaceDetailReview.adapter = reviewAdapter
        binding.rvPlaceDetailReview.addItemDecoration(ItemDecorationWithStroke(false))
    }

    private fun reportUser(userId: Int) {
        showUserReportDialog(userId)
    }

    private fun showUserReportDialog(userId: Int) {
        val dialog = AlertDialog.Builder(this).apply {
            setTitle("?????? ????????????")
            setMessage("?????? ????????? ?????????????????????????")
            setNegativeButton("??????") { _, _ -> }
            setPositiveButton("??????") { _, _ ->
                viewModel.reportUser(userId)
            }
        }
        dialog.create()
        dialog.show()
    }

    private fun reportPost(postId: Int) {
        showPostReportDialog(postId)
    }

    private fun showPostReportDialog(postId: Int) {
        val dialog = AlertDialog.Builder(this).apply {
            setTitle("????????? ????????????")
            setMessage("?????? ???????????? ?????????????????????????")
            setNegativeButton("??????") { _, _ -> }
            setPositiveButton("??????") { _, _ ->
                viewModel.reportFeed(postId)
            }
        }
        dialog.create()
        dialog.show()
    }

    private fun moveToFeedDetail(feedData: FeedAllDataItem) {
        startActivity(Intent(baseContext, FeedDetailActivity::class.java).apply {
            putExtra(FEED_DETAIL_DATA, feedData)
        })
    }

    private fun moveToPlaceDetail(placeId: Int) {
        Log.e(this.javaClass.name, "???????????? ?????? ??????")
    }

    private fun likePost(postId: Int, isMypost: Boolean) {
        viewModel.likePost(postId, isMypost)
    }

    private fun savePlace(placeData: FeedPlaceItem) {
        Log.e(this.javaClass.name, "???????????? ?????? ??????")
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.uiSettings.isZoomControlEnabled = false
        initData()
    }

    private fun initData() {
        viewModel.initData()
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
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${viewModel.getContact()}")))
        }
        binding.ivPlaceDetailShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, binding.acbPlaceDetailLink.text)
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
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(binding.acbPlaceDetailLink.text as String?)
                )
            )
        }
        binding.tvPlaceDetailPlaceUpdateSuggest.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://pf.kakao.com/_xgHYNb")))
        }
        binding.tvPlaceDetailMapCopy.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("??????", binding.tvPlaceDetailMapCopyAddress.text)
            clipboard.setPrimaryClip(clip)
        }
        binding.ivPlaceDetailCreateReview.setOnClickListener {
            startActivity(Intent(baseContext, FeedCreateActivity::class.java).apply {
                putExtra(FEED_POST_INFO, viewModel.getPostData())
            })
        }
        binding.tvPlaceDetailReviewMore.setOnClickListener {
            startActivity(Intent(baseContext, PlaceDetailFeedActivity::class.java).apply {
                putExtra(PLACE_DETAIL_FEED_MORE, viewModel.getPostData())
            })
        }
    }

    private fun setObserver() {
        viewModel.thumbImages.observe(this) {
            setThumbAdapter(it)
        }
        viewModel.isSave.observe(this) {
            updateSaveView(it, true)
        }
        viewModel.menuAllData.observe(this) {
            menuAdapter.submitList(it)
            if (it.isNullOrEmpty()) {
                binding.clPlaceDetailMenu.visibility = View.GONE
            } else {
                binding.clPlaceDetailMenu.visibility = View.VISIBLE
            }
        }
        viewModel.keyword.observe(this) {
            it.forEach { keywordItem ->
                Log.i("keyword", keywordItem.toString())
                val keywordView = layoutInflater.inflate(
                    R.layout.item_place_detail_keyword,
                    binding.llPlaceDetailKeyword,
                    false
                ) as ConstraintLayout

                val keywordBinding =
                    DataBindingUtil.bind<ItemPlaceDetailKeywordBinding>(keywordView)
                if (keywordItem != null) {
                    val keyword = when (keywordItem.keywordId) {
                        in (1..5) -> {
                            KeywordFacility.values()[keywordItem.keywordId - 1]
                        }
                        in (6..10) -> {
                            KeywordMood.values()[keywordItem.keywordId - 5 - 1]
                        }
                        else -> {
                            KeywordSatisfaction.values()[keywordItem.keywordId - 10 - 1]
                        }
                    }

                    getKeywordData(keyword)?.let { iconAndContentAndColor ->
                        keywordBinding?.ivPlaceDetailKeywordIcon?.background =
                            ContextCompat.getDrawable(baseContext, iconAndContentAndColor.first)
                        keywordBinding?.tvPlaceDetailKeywordContent?.text =
                            resources.getString(iconAndContentAndColor.second)
                        keywordBinding?.tvPlaceDetailKeywordCount?.text =
                            keywordItem.postCnt.toString()
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
        viewModel.placeData.observe(this) {
            binding.tvPlaceDetailTitle.text = it.name
            binding.tvPlaceDetailFeed.text = it.postCnt.toString()
            binding.tvPlaceDetailSave.text = it.myplaceCnt.toString()
            val saveDrawable = when (it.isMyplace) {
                true -> R.drawable.ic_save_filled
                false -> R.drawable.ic_save
            }
            updateSaveView(it.isMyplace, false)
            binding.ivPlaceDetailSave.setImageResource(saveDrawable)
            binding.ivPlaceDetailSave.imageTintList = when (it.isMyplace) {
                true -> ColorStateList.valueOf(
                    ContextCompat.getColor(
                        baseContext,
                        R.color.primary_green
                    )
                )
                false -> ColorStateList.valueOf(ContextCompat.getColor(baseContext, R.color.black))
            }
            binding.tvPlaceDetailType.text = it.category
            binding.tvPlaceDetailTime.text = it.openTime
            binding.tvPlaceDetailDesc.text = it.oneLineIntro
            binding.acbPlaceDetailLink.text = it.homepage
            binding.tvPlaceDetailMapCopyAddress.text = it.address

            it.latitude?.let{ latitude ->
                it.longitude?.let { longitude ->
                    val marker = Marker()
                    marker.position = LatLng(latitude, longitude)
                    marker.icon = OverlayImage.fromResource(R.drawable.ic_map_marker)
                    marker.map = naverMap
                }
            }
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

    private fun updateSaveView(isSave: Boolean, isClick: Boolean) {
        when (isSave) {
            true -> {
                binding.ivPlaceDetailSave.setImageResource(R.drawable.ic_save_filled)
                binding.ivPlaceDetailSave.imageTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(baseContext, R.color.primary_green)
                    )
                if (isClick) {
                    binding.tvPlaceDetailSave.text =
                        (binding.tvPlaceDetailSave.text.toString().toInt() + 1).toString()
                }
            }
            false -> {
                binding.ivPlaceDetailSave.setImageResource(R.drawable.ic_save)
                binding.ivPlaceDetailSave.imageTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(baseContext, R.color.black))
                if (isClick) {
                    binding.tvPlaceDetailSave.text =
                        (binding.tvPlaceDetailSave.text.toString().toInt() - 1).toString()
                }
            }
        }
    }

    companion object {
        const val PLACE_DETAIL_FEED_MORE =
            "com.gritbus.hipchon.ui.place.view PLACE_DETAIL_FEED_MORE"
        const val FEED_POST_INFO = "com.gritbus.hipchon.ui.place.view FEED_POST_INFO"
    }
}
