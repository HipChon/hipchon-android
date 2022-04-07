package com.gritbus.hipchon.ui.feed.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gritbus.hipchon.databinding.ItemFeedCreatePhotoBinding

class FeedCreatePhotoAdapter(
    private val deletePhoto: (Int) -> (Unit)
) : ListAdapter<Uri, FeedCreatePhotoAdapter.FeedCreatePhotoViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedCreatePhotoViewHolder {
        return FeedCreatePhotoViewHolder(
            ItemFeedCreatePhotoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FeedCreatePhotoViewHolder, position: Int) {
        holder.bind(currentList[position], position, deletePhoto)
    }

    class FeedCreatePhotoViewHolder(
        private val binding: ItemFeedCreatePhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Uri, position: Int, deletePhoto: (Int) -> (Unit)) {
            binding.ivCreatePhoto.setImageURI(data)
            binding.ivCreatePhotoDelete.setOnClickListener {
                deletePhoto(position)
            }
            binding.executePendingBindings()
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Uri>() {
            override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
                return oldItem == newItem
            }
        }
    }
}
