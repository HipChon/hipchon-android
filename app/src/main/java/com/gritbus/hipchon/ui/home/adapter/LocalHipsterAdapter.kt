package com.gritbus.hipchon.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gritbus.hipchon.databinding.ItemLocalHipsterPickBinding
import com.gritbus.hipchon.databinding.ItemLocalHipsterPickHeaderBinding

class LocalHipsterAdapter(
    private val fragmentManager: FragmentManager,
    private val lifecycle: Lifecycle
) : ListAdapter<Int, RecyclerView.ViewHolder>(diffUtil) {

    private val HEADER = 0
    private val CONTENT = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER -> {
                LocalHipsterHeaderViewHolder(
                    ItemLocalHipsterPickHeaderBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
            else -> {
                LocalHipsterViewHolder(
                    ItemLocalHipsterPickBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is LocalHipsterViewHolder -> holder.bind(fragmentManager, lifecycle)
            is LocalHipsterHeaderViewHolder -> holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HEADER
            else -> CONTENT
        }
    }

    class LocalHipsterHeaderViewHolder(
        private val binding: ItemLocalHipsterPickHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {}
    }

    class LocalHipsterViewHolder(
        private val binding: ItemLocalHipsterPickBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(fragmentManager: FragmentManager, lifecycle: Lifecycle) {
            binding.vpLocalHipsterPickThumb.adapter =
                LocalHipsterThumbAdapter(
                    listOf("1", "1", "1"),
                    fragmentManager,
                    lifecycle
                )
            binding.executePendingBindings()
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Int>() {
            override fun areContentsTheSame(
                oldItem: Int,
                newItem: Int
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: Int,
                newItem: Int
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
