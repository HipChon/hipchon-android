package com.gritbus.hipchon.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gritbus.hipchon.databinding.ItemHomeHipsterPickBinding
import com.gritbus.hipchon.domain.model.LocalHipsterData

class LocalHipsterAdapter :
    ListAdapter<LocalHipsterData, LocalHipsterAdapter.LocalHipsterViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalHipsterViewHolder {
        return LocalHipsterViewHolder(
            ItemHomeHipsterPickBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: LocalHipsterViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class LocalHipsterViewHolder(
        private val binding: ItemHomeHipsterPickBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(localHipsterData: LocalHipsterData) {
            binding.tvHomeHipsterPickArea.text = localHipsterData.area.value
            binding.tvHomeHipsterPickTitle.text = localHipsterData.title
            binding.tvHomeHipsterPickDesc.text = localHipsterData.desc
            Glide.with(binding.root.context)
                .load(localHipsterData.imageUrl)
                .into(binding.ivHomeHipsterPick)
            binding.executePendingBindings()
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<LocalHipsterData>() {
            override fun areContentsTheSame(
                oldItem: LocalHipsterData,
                newItem: LocalHipsterData
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: LocalHipsterData,
                newItem: LocalHipsterData
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
