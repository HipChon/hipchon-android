package com.gritbus.hipchon.ui.my.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gritbus.hipchon.data.model.my.MyFeedAllDataItem
import com.gritbus.hipchon.databinding.ItemMyReviewBinding

class MyReviewAdapter(
    private val clickListener: (Int) -> (Unit),
    private val deletePost: (Int) -> (Unit)
) :ListAdapter<MyFeedAllDataItem, MyReviewAdapter.MyReviewViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewViewHolder {
        return MyReviewViewHolder(
            ItemMyReviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyReviewViewHolder, position: Int) {
        holder.bind(currentList[position], clickListener, deletePost)
    }

    class MyReviewViewHolder(
        private val binding: ItemMyReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            myFeedAllDataItem: MyFeedAllDataItem,
            clickListener: (Int) -> (Unit),
            deletePost: (Int) -> (Unit)
        ) {
            binding.root.setOnClickListener {
                clickListener(myFeedAllDataItem.postId)
            }
            binding.ivMyReviewDelete.setOnClickListener {
                deletePost(myFeedAllDataItem.postId)
            }
            Glide.with(binding.root.context)
                .load(myFeedAllDataItem.image)
                .into(binding.ivMyReview)
            binding.tvMyReviewTitle.text = myFeedAllDataItem.name
            binding.ivMyReviewHashtag.visibility = View.INVISIBLE

            binding.executePendingBindings()
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MyFeedAllDataItem>() {
            override fun areItemsTheSame(
                oldItem: MyFeedAllDataItem,
                newItem: MyFeedAllDataItem
            ): Boolean {
                return oldItem.postId == newItem.postId
            }

            override fun areContentsTheSame(
                oldItem: MyFeedAllDataItem,
                newItem: MyFeedAllDataItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
