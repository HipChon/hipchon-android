package com.gritbus.hipchon.ui.feed.view

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ActivityFeedCreateBinding
import com.gritbus.hipchon.ui.feed.adapter.FeedCreatePhotoAdapter
import com.gritbus.hipchon.ui.feed.adapter.FeedKeywordAdapter
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.ItemDecorationWithHorizontalSpacing
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.ui.MatisseActivity

class FeedCreateActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityFeedCreateBinding>(R.layout.activity_feed_create) {

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

                    val bitmapAllData = data.map { uri ->
                        when {
                            Build.VERSION.SDK_INT > 28 -> {
                                val source = ImageDecoder.createSource(this.contentResolver, uri)
                                ImageDecoder.decodeBitmap(source)
                            }
                            else -> {
                                MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                            }
                        }

                    }
                    binding.rvFeedCreateSelectImage.visibility = View.VISIBLE
                    photoAdapter.submitList(bitmapAllData)
                }
            }
    }

    override fun initView() {
        setPhotoAdapter()
        setKeywordAdapter()
        setClickListener()
    }

    private fun setPhotoAdapter() {
        photoAdapter = FeedCreatePhotoAdapter()
        binding.rvFeedCreateSelectImage.adapter = photoAdapter
    }

    private fun setKeywordAdapter() {
        keywordAdapter = FeedKeywordAdapter()
        binding.rvFeedCreateKeyword.adapter = keywordAdapter
        binding.rvFeedCreateKeyword.addItemDecoration(ItemDecorationWithHorizontalSpacing(16))

        keywordAdapter.submitList(listOf("시설", "분위기", "만족도"))
    }

    private fun setClickListener() {
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
    }
}
