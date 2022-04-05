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
import com.gritbus.hipchon.utils.dateToFormattedString

class CommentAdapter(
    private val reportComment: (Int) -> (Unit)
) : ListAdapter<CommentAllDataItem, CommentAdapter.CommentViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            ItemFeedCommentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(currentList[position], reportComment)
    }

    class CommentViewHolder(
        private val binding: ItemFeedCommentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(commentAllDataItem: CommentAllDataItem, reportComment: (Int) -> (Unit)) {
            Glide.with(binding.root.context)
                .load(commentAllDataItem.user.image)
                .circleCrop()
                .error(R.drawable.ic_profile_default_gray)
                .into(binding.ivFeedCommentProfile)
            binding.tvFeedCommentName.text = commentAllDataItem.user.name
            binding.tvFeedCommentContent.text = commentAllDataItem.detail
            binding.tvFeedCommentTime.text = commentAllDataItem.time.dateToFormattedString()
            binding.tvFeedCommentReport.setOnClickListener {
                reportComment(commentAllDataItem.commentId)
            }

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
