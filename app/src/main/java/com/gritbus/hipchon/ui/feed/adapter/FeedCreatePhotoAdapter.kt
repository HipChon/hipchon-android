package com.gritbus.hipchon.ui.feed.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gritbus.hipchon.databinding.ItemFeedCreatePhotoBinding

class FeedCreatePhotoAdapter :
    ListAdapter<Bitmap, FeedCreatePhotoAdapter.FeedCreatePhotoViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedCreatePhotoViewHolder {
        return FeedCreatePhotoViewHolder(
            ItemFeedCreatePhotoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FeedCreatePhotoViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class FeedCreatePhotoViewHolder(
        private val binding: ItemFeedCreatePhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Bitmap) {
            binding.ivCreatePhoto.setImageBitmap(data)
            binding.executePendingBindings()
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Bitmap>() {
            override fun areItemsTheSame(oldItem: Bitmap, newItem: Bitmap): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Bitmap, newItem: Bitmap): Boolean {
                return oldItem == newItem
            }
        }
    }
}
