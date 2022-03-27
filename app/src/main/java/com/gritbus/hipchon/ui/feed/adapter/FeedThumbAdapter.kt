package com.gritbus.hipchon.ui.feed.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gritbus.hipchon.databinding.ItemFeedImageBinding
import com.gritbus.hipchon.utils.dpToPx

class FeedThumbAdapter : ListAdapter<String, FeedThumbAdapter.FeedThumbViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedThumbViewHolder {
        return FeedThumbViewHolder(
            ItemFeedImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FeedThumbViewHolder, position: Int) {
        holder.bind(currentList[position], position == currentList.lastIndex, currentList.size)
    }

    class FeedThumbViewHolder(
        private val binding: ItemFeedImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(feedImageUrl: String, isLastItem: Boolean, totalImageSize: Int) {
            binding.root.requestLayout()

            Glide.with(binding.root.context)
                .load(feedImageUrl)
                .into(binding.ivFeedImage)

            val horizontalPadding = dpToPx(binding.root.context, 40)
            val itemSpacing = dpToPx(binding.root.context, 4)

            if (totalImageSize == 1) {
                binding.root.layoutParams.width =
                    (binding.root.context.resources.displayMetrics.widthPixels - horizontalPadding)
            } else {
                binding.root.layoutParams.width =
                    (binding.root.context.resources.displayMetrics.widthPixels - horizontalPadding - itemSpacing) / 2
            }
            if (isLastItem && (totalImageSize > 2)) {
                binding.llFeedImageCover.visibility = View.VISIBLE
                binding.tvFeedImageCount.text = totalImageSize.toString()
            } else {
                binding.llFeedImageCover.visibility = View.INVISIBLE
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

