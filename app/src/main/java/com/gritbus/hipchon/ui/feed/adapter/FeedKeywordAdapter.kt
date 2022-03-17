package com.gritbus.hipchon.ui.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gritbus.hipchon.databinding.ItemFeedCreateKeywordBinding

class FeedKeywordAdapter : ListAdapter<String, FeedKeywordAdapter.FeedKeywordViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedKeywordViewHolder {
        return FeedKeywordViewHolder(
            ItemFeedCreateKeywordBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FeedKeywordViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class FeedKeywordViewHolder(
        private val binding: ItemFeedCreateKeywordBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: String) {
            binding.tvKeywordTitle.text = data
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}
