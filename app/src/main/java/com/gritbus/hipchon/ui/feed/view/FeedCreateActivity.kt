package com.gritbus.hipchon.ui.feed.view

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityFeedCreateBinding
import com.gritbus.hipchon.ui.feed.adapter.FeedCreatePhotoAdapter
import com.gritbus.hipchon.ui.feed.adapter.FeedKeywordAdapter
import com.gritbus.hipchon.ui.feed.viewmodel.FeedCreateViewModel
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.ItemDecorationWithHorizontalSpacing
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.ui.MatisseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedCreateActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityFeedCreateBinding>(R.layout.activity_feed_create) {

    private val viewModel: FeedCreateViewModel by viewModels()
    private lateinit var keywordAdapter: FeedKeywordAdapter
    private lateinit var photoAdapter: FeedCreatePhotoAdapter
    private lateinit var registerForActivity: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRegisterForActivityResult()
        initView()
    }

    private fun setRegisterForActivityResult() {
        registerForActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val data = Matisse.obtainResult(it.data)

                    binding.rvFeedCreateSelectImage.visibility = View.VISIBLE
                    photoAdapter.submitList(data)
                }
            }
    }

    override fun initView() {
        setPhotoAdapter()
        setKeywordAdapter()
        setClickListener()
        setObserver()
    }

    private fun setPhotoAdapter() {
        photoAdapter = FeedCreatePhotoAdapter(::photoDeleteListener)
        binding.rvFeedCreateSelectImage.adapter = photoAdapter
    }

    private fun photoDeleteListener(position: Int) {
        photoAdapter.submitList(photoAdapter.currentList.mapIndexedNotNull { index, uri ->
            if (index == position) null else uri
        })
    }

    private fun setKeywordAdapter() {
        keywordAdapter = FeedKeywordAdapter(::keywordClickListener)
        binding.rvFeedCreateKeyword.adapter = keywordAdapter
        binding.rvFeedCreateKeyword.addItemDecoration(ItemDecorationWithHorizontalSpacing(16))

        keywordAdapter.submitList(listOf("시설", "분위기", "만족도"))
    }

    private fun keywordClickListener(checkedKeywordList: List<Int>) {
        viewModel.updateCheckedKeywordList(checkedKeywordList)
    }

    private fun setClickListener() {
        binding.mtFeedCreate.setNavigationOnClickListener {
            finish()
        }
        binding.acbFeedCreateReviewContentPhoto.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Matisse.from(this)
                    .choose(MimeType.ofAll())
                    .countable(true)
                    .maxSelectable(9)
                    .imageEngine(GlideEngine())
                    .let {
                        val intent = Intent(this, MatisseActivity::class.java)
                        registerForActivity.launch(intent)
                    }
            } else {
                requestPermissions(
                    arrayOf(READ_EXTERNAL_STORAGE),
                    1000
                )
            }
        }
        binding.acbFeedCreate.setOnClickListener {
            if (viewModel.getCheckedKeywordList().isNullOrEmpty()) {
                Toast.makeText(this, "키워드를 선택해주세요!", Toast.LENGTH_SHORT).show()
            } else {
                if (binding.etFeedCreateReviewContent.text.toString().isBlank()) {
                    binding.etFeedCreateReviewContent.setText("꼭 다시 가고 싶은 곳이에요!")
                }
                viewModel.sendPost(
                    binding.etFeedCreateReviewContent.text.toString(),
                    photoAdapter.currentList
                )
            }
        }
    }

    private fun setObserver() {
        viewModel.placeData.observe(this) {
            binding.tvFeedCreatePlaceName.text = it.name
            Glide.with(baseContext)
                .load(it.imageList?.get(0))
                .into(binding.ivFeedCreatePlaceThumb)
        }
        viewModel.sendSuccess.observe(this) {
            if (it) {
                finish()
            }
        }
    }
}
