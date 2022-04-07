package com.gritbus.hipchon.ui.place.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gritbus.hipchon.databinding.ItemPlaceMenuBinding

class PlaceMenuAdapter : ListAdapter<String, PlaceMenuAdapter.PlaceMenuViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceMenuViewHolder {
        return PlaceMenuViewHolder(
            ItemPlaceMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlaceMenuViewHolder, position: Int) {
        holder.bind()
    }

    class PlaceMenuViewHolder(
        private val binding: ItemPlaceMenuBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {}
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
