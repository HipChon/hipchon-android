package com.gritbus.hipchon.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ItemHomeWeeklyHipPlaceBinding
import com.gritbus.hipchon.domain.model.WeeklyHipPlaceData

class WeeklyHipPlaceAdapter(
    private val saveClickCallback: (WeeklyHipPlaceData) -> (Unit)
) : ListAdapter<WeeklyHipPlaceData, WeeklyHipPlaceAdapter.WeeklyHipPlaceViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyHipPlaceViewHolder {
        return WeeklyHipPlaceViewHolder(
            ItemHomeWeeklyHipPlaceBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WeeklyHipPlaceViewHolder, position: Int) {
        holder.bind(currentList[position], saveClickCallback)
    }

    class WeeklyHipPlaceViewHolder(
        private val binding: ItemHomeWeeklyHipPlaceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            weeklyHipPlaceData: WeeklyHipPlaceData,
            saveClickCallback: (WeeklyHipPlaceData) -> Unit
        ) {
            binding.ivHomeWeeklyHipPlaceSave.setOnClickListener {
                saveClickCallback(weeklyHipPlaceData)
            }
            binding.tvHomeWeeklyHipPlaceTitle.text = weeklyHipPlaceData.title
            binding.tvHomeWeeklyHipPlaceArea.text = weeklyHipPlaceData.area.value
            binding.tvHomeWeeklyHipPlaceKeyword1st.text = weeklyHipPlaceData.keywordFirst
            binding.tvHomeWeeklyHipPlaceKeyword2nd.text = weeklyHipPlaceData.keywordSecond
            binding.ivHomeWeeklyHipPlaceSave.background = when (weeklyHipPlaceData.isSave) {
                true -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_save_filled)
                false -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_save)
            }
            binding.tvHomeWeeklyHipPlaceSave.text = binding.root.context.resources.getString(
                R.string.weekly_hip_place_save_count,
                weeklyHipPlaceData.saveCount
            )
            binding.tvHomeWeeklyHipPlaceFeed.text = binding.root.context.resources.getString(
                R.string.weekly_hip_place_feed_count,
                weeklyHipPlaceData.feedCount
            )
            Glide.with(binding.root.context)
                .load(weeklyHipPlaceData.imageUrl)
                .into(binding.ivHomeWeeklyHipPlaceThumbnail)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<WeeklyHipPlaceData>() {
            override fun areItemsTheSame(
                oldItem: WeeklyHipPlaceData,
                newItem: WeeklyHipPlaceData
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: WeeklyHipPlaceData,
                newItem: WeeklyHipPlaceData
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
