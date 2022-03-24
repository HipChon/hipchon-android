package com.gritbus.hipchon.ui.my.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentMyReviewBinding
import com.gritbus.hipchon.ui.my.adapter.MyReviewAdapter
import com.gritbus.hipchon.ui.my.adapter.MyReviewCommentAdapter
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.ItemDecorationWithStroke
import com.gritbus.hipchon.utils.dpToPx
import com.tbuonomo.viewpagerdotsindicator.setPaddingHorizontal

class MyReviewFragment :
    BaseViewUtil.BaseFragment<FragmentMyReviewBinding>(R.layout.fragment_my_review) {

    private lateinit var fragmentType: String
    private lateinit var myReviewAdapter: Any

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        arguments?.getString(MY_REVIEW_FRAGMENT_TYPE)?.let {
            fragmentType = it
        }
        setAdapter()
    }

    private fun setAdapter() {
        myReviewAdapter = when (fragmentType) {
            "내가 쓴 댓글" -> {
                MyReviewCommentAdapter()
            }
            else -> {
                MyReviewAdapter()
            }
        }
        binding.rvMyReview.adapter = myReviewAdapter as RecyclerView.Adapter<*>
        when (myReviewAdapter) {
            is MyReviewAdapter -> {
                binding.rvMyReview.layoutManager = GridLayoutManager(requireContext(), 2)
                (myReviewAdapter as MyReviewAdapter)
                    .submitList(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
            }
            is MyReviewCommentAdapter -> {
                binding.rvMyReview.layoutManager = LinearLayoutManager(requireContext())
                binding.rvMyReview.setPaddingHorizontal(dpToPx(requireContext(), 0))
                binding.rvMyReview.addItemDecoration(ItemDecorationWithStroke(false))
                (myReviewAdapter as MyReviewCommentAdapter)
                    .submitList(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
            }
        }
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
