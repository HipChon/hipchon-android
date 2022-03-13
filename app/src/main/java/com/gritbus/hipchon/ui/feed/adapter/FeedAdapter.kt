package com.gritbus.hipchon.ui.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gritbus.hipchon.databinding.ItemFeedPreviewBinding

class FeedAdapter(
    private val clickListener: () -> (Unit)
) : ListAdapter<Int, FeedAdapter.FeedViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(
            ItemFeedPreviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(clickListener)
    }

    class FeedViewHolder(
        private val binding: ItemFeedPreviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: () -> Unit) {
            binding.root.setOnClickListener {
                clickListener()
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }
        }
    }
}
