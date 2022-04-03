package com.gritbus.hipchon.ui.my.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gritbus.hipchon.data.model.my.MyCommentAllDataItem
import com.gritbus.hipchon.databinding.ItemMyReviewCommentBinding

class MyReviewCommentAdapter(
    private val clickListener: (Int) -> (Unit),
    private val deleteClickListener: (Int) -> Unit
) : ListAdapter<MyCommentAllDataItem, MyReviewCommentAdapter.MyReviewCommentViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewCommentViewHolder {
        return MyReviewCommentViewHolder(
            ItemMyReviewCommentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyReviewCommentViewHolder, position: Int) {
        holder.bind(currentList[position], clickListener, deleteClickListener)
    }

    class MyReviewCommentViewHolder(
        private val binding: ItemMyReviewCommentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            myCommentAllDataItem: MyCommentAllDataItem,
            clickListener: (Int) -> (Unit),
            deleteClickListener: (Int) -> (Unit)
        ) {
            binding.root.setOnClickListener {
                clickListener(myCommentAllDataItem.post.postId)
            }
            binding.ivMyReviewCommentDelete.setOnClickListener {
                deleteClickListener(myCommentAllDataItem.commentId)
            }

            Glide.with(binding.root.context)
                .load(myCommentAllDataItem.post.image)
                .into(binding.ivMyReviewCommentThumb)
            binding.tvMyReviewComment.text = myCommentAllDataItem.detail
            binding.tvMyReviewCommentDate.text = myCommentAllDataItem.time

            binding.executePendingBindings()
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MyCommentAllDataItem>() {
            override fun areItemsTheSame(
                oldItem: MyCommentAllDataItem,
                newItem: MyCommentAllDataItem
            ): Boolean {
                return oldItem.commentId == newItem.commentId
            }

            override fun areContentsTheSame(
                oldItem: MyCommentAllDataItem,
                newItem: MyCommentAllDataItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
