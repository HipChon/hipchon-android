package com.gritbus.hipchon.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gritbus.hipchon.R
import com.gritbus.hipchon.data.model.place.PlaceHipSearchAllDataItem
import com.gritbus.hipchon.databinding.ItemHomeWeeklyHipPlaceBinding

class WeeklyHipPlaceAdapter(
    private val clickListener: (Int) -> (Unit),
    private val saveClickCallback: (PlaceHipSearchAllDataItem) -> (Unit)
) : ListAdapter<PlaceHipSearchAllDataItem, WeeklyHipPlaceAdapter.WeeklyHipPlaceViewHolder>(diffUtil) {

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
        holder.bind(clickListener, currentList[position], saveClickCallback)
    }

    class WeeklyHipPlaceViewHolder(
        private val binding: ItemHomeWeeklyHipPlaceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            clickListener: (Int) -> (Unit),
            weeklyHipPlaceData: PlaceHipSearchAllDataItem,
            saveClickCallback: (PlaceHipSearchAllDataItem) -> Unit
        ) {
            binding.root.setOnClickListener {
                clickListener(weeklyHipPlaceData.placeId)
            }
            binding.ivHomeWeeklyHipPlaceSave.setOnClickListener {
                saveClickCallback(weeklyHipPlaceData)
            }
            binding.tvHomeWeeklyHipPlaceTitle.text = weeklyHipPlaceData.name
            binding.tvHomeWeeklyHipPlaceArea.text = weeklyHipPlaceData.category
            binding.tvHomeWeeklyHipPlaceKeyword.text = "업데이트 대기중"
            binding.ivHomeWeeklyHipPlaceSave.background = when (weeklyHipPlaceData.isMyplace) {
                true -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_save_filled)
                false -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_save)
            }
            binding.tvHomeWeeklyHipPlaceSaveCount.text = weeklyHipPlaceData.myplaceCnt.toString()
            binding.tvHomeWeeklyHipPlaceFeedCount.text = weeklyHipPlaceData.postCnt.toString()
            Glide.with(binding.root.context)
                .load(weeklyHipPlaceData.placeImage)
                .into(binding.ivHomeWeeklyHipPlaceThumbnail)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PlaceHipSearchAllDataItem>() {
            override fun areItemsTheSame(
                oldItem: PlaceHipSearchAllDataItem,
                newItem: PlaceHipSearchAllDataItem
            ): Boolean {
                return oldItem.placeId == newItem.placeId
            }

            override fun areContentsTheSame(
                oldItem: PlaceHipSearchAllDataItem,
                newItem: PlaceHipSearchAllDataItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
