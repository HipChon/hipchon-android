package com.gritbus.hipchon.ui.my.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.FragmentMyReviewBinding
import com.gritbus.hipchon.ui.my.adapter.MyReviewAdapter
import com.gritbus.hipchon.ui.my.adapter.MyReviewCommentAdapter
import com.gritbus.hipchon.ui.my.view.MyFragment.Companion.MY_COMMENT
import com.gritbus.hipchon.ui.my.viewmodel.MyViewModel
import com.gritbus.hipchon.utils.BaseViewUtil
import com.gritbus.hipchon.utils.ItemDecorationWithStroke
import com.gritbus.hipchon.utils.dpToPx
import com.tbuonomo.viewpagerdotsindicator.setPaddingHorizontal
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyReviewFragment :
    BaseViewUtil.BaseFragment<FragmentMyReviewBinding>(R.layout.fragment_my_review) {

    private val viewModel: MyViewModel by viewModels()
    private lateinit var myReviewAdapter: Any

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        setObserver()
        viewModel.getMyData()
    }

    private fun setObserver() {
        viewModel.dataType.observe(viewLifecycleOwner) {
            myReviewAdapter = when (it) {
                MY_COMMENT -> {
                    MyReviewCommentAdapter()
                }
                else -> {
                    MyReviewAdapter()
                }
            }
            setAdapter()
        }
        viewModel.myFeedAllData.observe(viewLifecycleOwner) {
            (myReviewAdapter as MyReviewAdapter)
                .submitList(it)
            if (it.isNullOrEmpty()){
                binding.llMyReviewEmpty.visibility = View.VISIBLE
            } else {
                binding.llMyReviewEmpty.visibility = View.INVISIBLE
            }
        }
        viewModel.myCommentAllData.observe(viewLifecycleOwner) {
            (myReviewAdapter as MyReviewCommentAdapter)
                .submitList(it)
            if (it.isNullOrEmpty()){
                binding.llMyReviewEmpty.visibility = View.VISIBLE
            } else {
                binding.llMyReviewEmpty.visibility = View.INVISIBLE
            }
        }
    }

    private fun setAdapter() {
        binding.rvMyReview.adapter = myReviewAdapter as RecyclerView.Adapter<*>
        when (myReviewAdapter) {
            is MyReviewAdapter -> {
                binding.rvMyReview.layoutManager = GridLayoutManager(requireContext(), 2)
            }
            is MyReviewCommentAdapter -> {
                binding.rvMyReview.layoutManager = LinearLayoutManager(requireContext())
                binding.rvMyReview.setPaddingHorizontal(dpToPx(requireContext(), 0))
                binding.rvMyReview.addItemDecoration(ItemDecorationWithStroke(false))
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
