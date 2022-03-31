package com.gritbus.hipchon.ui.home.adapter

import android.content.res.ColorStateList
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
import com.gritbus.hipchon.domain.model.KeywordFacility
import com.gritbus.hipchon.domain.model.KeywordMood
import com.gritbus.hipchon.domain.model.KeywordSatisfaction

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
//            binding.ivHomeWeeklyHipPlaceSave.setOnClickListener {
//                saveClickCallback(weeklyHipPlaceData)
//            }
            binding.tvHomeWeeklyHipPlaceTitle.text = weeklyHipPlaceData.name
            binding.tvHomeWeeklyHipPlaceArea.text = weeklyHipPlaceData.category

            binding.tvHomeWeeklyHipPlaceKeyword.text =
                weeklyHipPlaceData.keyword?.keyword ?: "업데이트 대기중"
            weeklyHipPlaceData.keyword?.let {
                val keywordValue = when (weeklyHipPlaceData.keyword.keywordId) {
                    in (1..5) -> {
                        KeywordFacility.values()[weeklyHipPlaceData.keyword.keywordId - 1].iconDrawable to R.color.secondary_yellow
                    }
                    in (6..10) -> {
                        KeywordMood.values()[weeklyHipPlaceData.keyword.keywordId - 5 - 1].iconDrawable to R.color.primary_green
                    }
                    else -> {
                        KeywordSatisfaction.values()[weeklyHipPlaceData.keyword.keywordId - 10 - 1].iconDrawable to R.color.secondary_blue
                    }
                }
                binding.ivHomeWeeklyHipPlaceKeyword.setImageResource(keywordValue.first)
                binding.llHomeWeeklyHipPlaceKeyword.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        binding.root.context,
                        keywordValue.second
                    )
                )
            }

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
