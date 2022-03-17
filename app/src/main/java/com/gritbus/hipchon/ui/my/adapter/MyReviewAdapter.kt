package com.gritbus.hipchon.ui.my.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gritbus.hipchon.databinding.ItemMyReviewBinding

class MyReviewAdapter : ListAdapter<Int, MyReviewAdapter.MyReviewViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewViewHolder {
        return MyReviewViewHolder(
            ItemMyReviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyReviewViewHolder, position: Int) {
        holder.bind()
    }

    class MyReviewViewHolder(
        private val binding: ItemMyReviewBinding
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
