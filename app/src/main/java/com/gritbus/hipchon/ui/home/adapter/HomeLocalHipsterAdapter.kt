package com.gritbus.hipchon.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gritbus.hipchon.data.model.place.LocalHipsterAllDataItem
import com.gritbus.hipchon.databinding.ItemHomeHipsterPickBinding

class HomeLocalHipsterAdapter(
    private val clickListener: (Int) -> (Unit)
) : ListAdapter<LocalHipsterAllDataItem, HomeLocalHipsterAdapter.HomeLocalHipsterViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeLocalHipsterViewHolder {
        return HomeLocalHipsterViewHolder(
            ItemHomeHipsterPickBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeLocalHipsterViewHolder, position: Int) {
        holder.bind(currentList[position], clickListener)
    }

    class HomeLocalHipsterViewHolder(
        private val binding: ItemHomeHipsterPickBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(localHipsterData: LocalHipsterAllDataItem, clickListener: (Int) -> (Unit)) {
            binding.tvHomeHipsterPickArea.text = localHipsterData.city
            binding.tvHomeHipsterPickTitle.text = localHipsterData.title
            binding.tvHomeHipsterPickDesc.text = localHipsterData.summary
            Glide.with(binding.root.context)
                .load(localHipsterData.image)
                .into(binding.ivHomeHipsterPick)

            binding.root.setOnClickListener {
                clickListener(localHipsterData.hipsterId)
            }
            binding.executePendingBindings()
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<LocalHipsterAllDataItem>() {
            override fun areContentsTheSame(
                oldItem: LocalHipsterAllDataItem,
                newItem: LocalHipsterAllDataItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: LocalHipsterAllDataItem,
                newItem: LocalHipsterAllDataItem
            ): Boolean {
                return oldItem.hipsterId == newItem.hipsterId
            }
        }
    }
}
