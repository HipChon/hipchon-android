package com.gritbus.hipchon.ui.place.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gritbus.hipchon.R
import com.gritbus.hipchon.data.model.place.PlaceSearchAllDataItem
import com.gritbus.hipchon.databinding.ItemPlaceResultBinding

class PlaceResultAdapter(
    private val fragmentManager: FragmentManager,
    private val lifecycle: Lifecycle,
    private val detailClickCallback: (Int) -> (Unit)
) : ListAdapter<PlaceSearchAllDataItem, PlaceResultAdapter.PlaceResultViewHolder>(diffUtil) {

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
        holder.bind(
            fragmentManager,
            lifecycle,
            currentList[position],
            detailClickCallback
        )
    }

    class PlaceResultViewHolder(
        private val binding: ItemPlaceResultBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            fragmentManager: FragmentManager,
            lifecycle: Lifecycle,
            placeData: PlaceSearchAllDataItem,
            detailClickCallback: (Int) -> (Unit)
        ) {
            binding.root.setOnClickListener {
                detailClickCallback(placeData.placeId)
            }
            binding.vpHomePlaceThumbnail.adapter =
                PlaceResultImageAdapter(placeData.imageList, fragmentManager, lifecycle)
            binding.ivHomePlaceSaveCount.background = when (placeData.isMyplace) {
                true -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_save_filled)
                false -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_save)
            }
            binding.tvHomePlaceTitle.text = placeData.name
            binding.tvHomePlaceSaveCount.text = placeData.myplaceCnt.toString()
            binding.tvHomePlaceFeedCount.text = placeData.postCnt.toString()

            binding.executePendingBindings()
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PlaceSearchAllDataItem>() {
            override fun areItemsTheSame(oldItem: PlaceSearchAllDataItem, newItem: PlaceSearchAllDataItem): Boolean {
                return oldItem.placeId == newItem.placeId
            }

            override fun areContentsTheSame(oldItem: PlaceSearchAllDataItem, newItem: PlaceSearchAllDataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
