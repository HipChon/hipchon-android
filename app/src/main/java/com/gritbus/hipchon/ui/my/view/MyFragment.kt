package com.gritbus.hipchon.ui.my.view

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentMyBinding
import com.gritbus.hipchon.ui.MainActivity
import com.gritbus.hipchon.ui.my.adapter.MyAdapter
import com.gritbus.hipchon.utils.BaseViewUtil

class MyFragment : BaseViewUtil.BaseFragment<FragmentMyBinding>(R.layout.fragment_my) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        setAdapter()
        setTabLayout()
        setOnClickListener()
    }

    private fun setAdapter() {
        binding.vpMy.adapter = MyAdapter(this)
    }

    private fun setTabLayout() {
        val titleList = listOf("내가 심은 모", "좋아요 한 모", "내가 쓴 댓글")
        TabLayoutMediator(binding.tlMy, binding.vpMy) { tab, position ->
            tab.text = titleList[position]
        }.attach()
    }

    private fun setOnClickListener(){
        binding.ivMyProfile.setOnClickListener {
            (activity as? MainActivity)?.addFragment(MyUpdateFragment())
        }
    }
}
