package com.gritbus.hipchon.ui.save.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gritbus.hipchon.databinding.ItemSavePlaceBinding

class SavePlaceAdapter : ListAdapter<Int, SavePlaceAdapter.SavePlaceViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavePlaceViewHolder {
        return SavePlaceViewHolder(
            ItemSavePlaceBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SavePlaceViewHolder, position: Int) {
        holder.bind()
    }

    class SavePlaceViewHolder(
        private val binding: ItemSavePlaceBinding
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
