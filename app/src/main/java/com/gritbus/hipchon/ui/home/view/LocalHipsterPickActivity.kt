package com.gritbus.hipchon.ui.home.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.gritbus.hipchon.R
import com.gritbus.hipchon.data.model.place.LocalHipsterPlace
import com.gritbus.hipchon.data.model.place.LocalHipsterPost
import com.gritbus.hipchon.databinding.ActivityLocalHipsterPickBinding
import com.gritbus.hipchon.ui.home.adapter.LocalHipsterAdapter
import com.gritbus.hipchon.ui.home.viewmodel.LocalHipsterViewModel
import com.gritbus.hipchon.ui.place.view.PlaceDetailActivity
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.ItemDecorationWithStroke
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocalHipsterPickActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivityLocalHipsterPickBinding>(R.layout.activity_local_hipster_pick) {

    private lateinit var localHipsterAdapter: LocalHipsterAdapter
    private val viewModel: LocalHipsterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
        setAdapter()
        setOnClickListener()
        setObserver()
        viewModel.getLocalHipsterData()
    }

    private fun setObserver() {
        viewModel.localHipsterData.observe(this) {
            val localHipsterList = it.postList.toMutableList()
            localHipsterList.add(
                0, LocalHipsterPost(
                    "", 0, listOf(), LocalHipsterPlace(
                        "", "", "", false, "", 0
                    ), it.title
                )
            )
            localHipsterAdapter.submitList(localHipsterList)
        }
    }

    private fun setAdapter() {
        localHipsterAdapter =
            LocalHipsterAdapter(
                ::placeClickListener,
                supportFragmentManager,
                lifecycle
            )
        binding.rvLocalHipsterPick.adapter = localHipsterAdapter
        binding.rvLocalHipsterPick.addItemDecoration(ItemDecorationWithStroke(true))
    }

    private fun placeClickListener() {
        startActivity(Intent(baseContext, PlaceDetailActivity::class.java))
    }

    private fun setOnClickListener() {
        binding.mtLocalHipsterPick.setNavigationOnClickListener {
            finish()
        }
    }
}
