package com.gritbus.hipchon.ui.my.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentMyReviewBinding
import com.gritbus.hipchon.ui.my.adapter.MyReviewCommentAdapter
import com.gritbus.hipchon.ui.my.view.MyReviewFragment.Companion.MY_REVIEW_FRAGMENT_TYPE
import com.gritbus.hipchon.ui.my.viewmodel.MyViewModel
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.ItemDecorationWithStroke
import com.gritbus.hipchon.utils.dpToPx
import com.tbuonomo.viewpagerdotsindicator.setPaddingHorizontal

class MyCommentFragment: BaseViewUtil.BaseFragment<FragmentMyReviewBinding>(R.layout.fragment_my_review) {

    private lateinit var type: String
    private val viewModel: MyViewModel by activityViewModels()
    private lateinit var myReviewAdapter: MyReviewCommentAdapter

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
        viewModel.myCommentAllData.observe(viewLifecycleOwner) {
            myReviewAdapter.submitList(it)
            if (it.isNullOrEmpty()){
                binding.llMyReviewEmpty.visibility = View.VISIBLE
            } else {
                binding.llMyReviewEmpty.visibility = View.INVISIBLE
            }
        }
    }

    private fun setAdapter() {
        myReviewAdapter = MyReviewCommentAdapter()
        binding.rvMyReview.adapter = myReviewAdapter
        binding.rvMyReview.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMyReview.setPaddingHorizontal(dpToPx(requireContext(), 0))
        binding.rvMyReview.addItemDecoration(ItemDecorationWithStroke(false))
    }

    companion object {
        fun createMyCommentFragment(
            fragmentType: String
        ): MyCommentFragment {
            return MyCommentFragment().apply {
                arguments = bundleOf(
                    MY_REVIEW_FRAGMENT_TYPE to fragmentType
                )
            }
        }
    }
}
