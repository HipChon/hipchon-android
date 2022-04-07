package com.gritbus.hipchon.ui.my.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentMyBinding
import com.gritbus.hipchon.ui.MainActivity
import com.gritbus.hipchon.ui.my.adapter.MyAdapter
import com.gritbus.hipchon.ui.my.viewmodel.MyViewModel
import com.gritbus.hipchon.utils.BaseViewUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFragment : BaseViewUtil.BaseFragment<FragmentMyBinding>(R.layout.fragment_my) {

    private val viewModel: MyViewModel by activityViewModels()
    private val titleList = listOf(MY_FEED, MY_LIKE_FEED, MY_COMMENT)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        setAdapter()
        setTabLayout()
        setOnClickListener()
        setObserver()
        viewModel.getMyProfile()
    }

    private fun setObserver() {
        viewModel.myProfile.observe(viewLifecycleOwner) {
            binding.tvMyName.text = it.name
            Glide.with(requireContext())
                .load(it.image)
                .circleCrop()
                .error(R.drawable.ic_profile_default_gray)
                .into(binding.ivMyProfile)
        }
    }

    private fun setAdapter() {
        binding.vpMy.adapter = MyAdapter(titleList, this)
    }

    private fun setTabLayout() {
        TabLayoutMediator(binding.tlMy, binding.vpMy) { tab, position ->
            tab.text = titleList[position]
        }.attach()
    }

    private fun setOnClickListener(){
        binding.ivMyProfile.setOnClickListener {
            (activity as? MainActivity)?.addFragment(MyUpdateFragment())
        }
        binding.ivMyOption.setOnClickListener {
            startActivity(Intent(requireContext(), MySettingActivity::class.java))
        }
    }

    companion object {
        const val MY_FEED = "내가 심은 모"
        const val MY_LIKE_FEED = "좋아요 한 모"
        const val MY_COMMENT = "내가 쓴 댓글"
    }
}
