package com.gritbus.hipchon.ui.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gritbus.hipchon.R
import com.gritbus.hipchon.data.model.feed.CommentAllDataItem
import com.gritbus.hipchon.databinding.ItemFeedCommentBinding

class CommentAdapter : ListAdapter<CommentAllDataItem, CommentAdapter.CommentViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            ItemFeedCommentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class CommentViewHolder(
        private val binding: ItemFeedCommentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(commentAllDataItem: CommentAllDataItem) {
            Glide.with(binding.root.context)
                .load(commentAllDataItem.user.image)
                .error(R.drawable.ic_profile_default_gray)
                .into(binding.ivFeedCommentProfile)
            binding.tvFeedCommentName.text = commentAllDataItem.user.name
            binding.tvFeedCommentContent.text = commentAllDataItem.detail
            binding.tvFeedCommentTime.text = commentAllDataItem.time

            binding.executePendingBindings()
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CommentAllDataItem>() {
            override fun areItemsTheSame(oldItem: CommentAllDataItem, newItem: CommentAllDataItem): Boolean {
                return oldItem.commentId == newItem.commentId
            }

            override fun areContentsTheSame(oldItem: CommentAllDataItem, newItem: CommentAllDataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
