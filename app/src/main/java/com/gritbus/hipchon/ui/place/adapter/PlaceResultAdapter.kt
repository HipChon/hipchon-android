package com.gritbus.hipchon.ui.place.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ItemPlaceResultBinding
import com.gritbus.hipchon.domain.model.PlaceData

class PlaceResultAdapter(
    private val saveClickCallback: (PlaceData) -> (Unit)
) : ListAdapter<PlaceData, PlaceResultAdapter.PlaceResultViewHolder>(diffUtil) {

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
        holder.bind(currentList[position], saveClickCallback)
    }

    class PlaceResultViewHolder(
        private val binding: ItemPlaceResultBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(placeData: PlaceData, saveClickCallback: (PlaceData) -> (Unit)) {
            binding.ivItemHomePlaceSave.setOnClickListener {
                saveClickCallback(placeData)
            }
            Glide.with(binding.root.context)
                .load(placeData.thumbnail[1])
                .placeholder(R.color.gray02)
                .into(binding.ivItemHomePlaceThumbnail)
            binding.ivItemHomePlaceSave.background = when (placeData.isSave) {
                true -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_save_filled)
                false -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_save)
            }
            binding.tvItemHomePlaceThumbnail.text = binding.root.context.resources.getString(
                R.string.place_result_thumbnail_count,
                1,
                placeData.thumbnail.size
            )
            binding.tvItemHomePlaceTitle.text = placeData.title
            binding.tvItemHomePlaceDesc.text = placeData.distance.toString()
            binding.tvItemHomePlaceKeyword1st.text = placeData.keywordFirst
            binding.tvItemHomePlaceKeyword2nd.text = placeData.keywordSecond
            binding.tvItemHomePlaceKeyword3rd.text = placeData.keywordThird
            binding.tvItemHomePlaceSaveCount.text = placeData.saveCount.toString()
            binding.tvItemHomePlaceFeedCount.text = placeData.feedCount.toString()
            binding.tvItemHomePlacePrice.text = placeData.price.toString()

            binding.executePendingBindings()
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PlaceData>() {
            override fun areItemsTheSame(oldItem: PlaceData, newItem: PlaceData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PlaceData, newItem: PlaceData): Boolean {
                return oldItem == newItem
            }
        }
    }
}
