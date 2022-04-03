package com.gritbus.hipchon.ui.my.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentMyReviewBinding
import com.gritbus.hipchon.ui.my.adapter.MyReviewAdapter
import com.gritbus.hipchon.ui.my.viewmodel.MyViewModel
import com.gritbus.hipchon.utils.BaseViewUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyReviewFragment :
    BaseViewUtil.BaseFragment<FragmentMyReviewBinding>(R.layout.fragment_my_review) {

    private lateinit var type: String
    private val viewModel: MyViewModel by activityViewModels()
    private lateinit var myReviewAdapter: MyReviewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        arguments?.getString(MY_REVIEW_FRAGMENT_TYPE)?.let {
            type = it
        }
        setAdapter()
        setObserver()
        viewModel.getMyData(type)
    }

    private fun setObserver() {
        viewModel.myFeedAllData.observe(viewLifecycleOwner) {
            myReviewAdapter.submitList(it)
            if (it.isNullOrEmpty()){
                binding.llMyReviewEmpty.visibility = View.VISIBLE
            } else {
                binding.llMyReviewEmpty.visibility = View.INVISIBLE
            }
        }
    }

    private fun setAdapter() {
        myReviewAdapter = MyReviewAdapter()
        binding.rvMyReview.adapter = myReviewAdapter
        binding.rvMyReview.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    companion object {
        const val MY_REVIEW_FRAGMENT_TYPE = "com.gritbus.hipchon.ui.my.view MY_REVIEW_FRAGMENT_TYPE"

        fun createMyReviewFragment(
            fragmentType: String
        ): MyReviewFragment {
            return MyReviewFragment().apply {
                arguments = bundleOf(
                    MY_REVIEW_FRAGMENT_TYPE to fragmentType
                )
            }
        }
    }
}
