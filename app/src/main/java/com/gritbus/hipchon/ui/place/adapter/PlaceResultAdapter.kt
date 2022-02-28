package com.gritbus.hipchon.ui.place.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gritbus.hipchon.databinding.ItemPlaceResultBinding

class PlaceResultAdapter : ListAdapter<String, PlaceResultAdapter.PlaceResultViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceResultViewHolder {
        return PlaceResultViewHolder(
            ItemPlaceResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlaceResultViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class PlaceResultViewHolder(
        private val binding: ItemPlaceResultBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {

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
