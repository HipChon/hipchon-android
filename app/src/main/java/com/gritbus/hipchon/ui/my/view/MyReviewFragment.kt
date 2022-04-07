package com.gritbus.hipchon.ui.my.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
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
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMyData(type)
    }

    private fun setObserver() {
        when(type) {
            MyFragment.MY_FEED -> {
                viewModel.myFeedAllData.observe(viewLifecycleOwner) {
                    myReviewAdapter.submitList(it)
                    if (it.isNullOrEmpty()){
                        binding.llMyReviewEmpty.visibility = View.VISIBLE
                    } else {
                        binding.llMyReviewEmpty.visibility = View.INVISIBLE
                    }
                }
                viewModel.postDeleteSuccess.observe(viewLifecycleOwner) {
                    if (it == true){
                        Snackbar.make(binding.root, "게시글을 삭제하였습니다.", Snackbar.LENGTH_LONG).show()
                        viewModel.resetDeletePostStatus()
                        viewModel.getMyFeedData()
                    }
                }
            }
            MyFragment.MY_LIKE_FEED -> {
                viewModel.myLikeFeedAllData.observe(viewLifecycleOwner) {
                    myReviewAdapter.submitList(it)
                    if (it.isNullOrEmpty()){
                        binding.llMyReviewEmpty.visibility = View.VISIBLE
                    } else {
                        binding.llMyReviewEmpty.visibility = View.INVISIBLE
                    }
                }
                viewModel.postLikeDeleteSuccess.observe(viewLifecycleOwner) {
                    if (it == true){
                        Snackbar.make(binding.root, "좋아요를 취소하였습니다.", Snackbar.LENGTH_LONG).show()
                        viewModel.resetDeletePostLikeStatus()
                        viewModel.getMyLikeFeedData()
                    }
                }
            }
        }
    }

    private fun setAdapter() {
        myReviewAdapter = MyReviewAdapter(::clickListener, ::deletePost)
        binding.rvMyReview.adapter = myReviewAdapter
        binding.rvMyReview.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun clickListener(postId: Int) {
        viewModel.getPostDetail(postId)
    }

    private fun deletePost(postId: Int) {
        when(type) {
            MyFragment.MY_FEED -> {
                viewModel.deletePost(postId)
            }
            MyFragment.MY_LIKE_FEED -> {
                viewModel.deletePostLike(postId)
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
