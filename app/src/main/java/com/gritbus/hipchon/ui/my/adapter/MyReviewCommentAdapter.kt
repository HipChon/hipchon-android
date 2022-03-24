package com.gritbus.hipchon.ui.my.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gritbus.hipchon.databinding.ItemMyReviewCommentBinding

class MyReviewCommentAdapter : ListAdapter<Int, MyReviewCommentAdapter.MyReviewCommentViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewCommentViewHolder {
        return MyReviewCommentViewHolder(
            ItemMyReviewCommentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyReviewCommentViewHolder, position: Int) {
        holder.bind()
    }

    class MyReviewCommentViewHolder(
        private val binding: ItemMyReviewCommentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {}
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
