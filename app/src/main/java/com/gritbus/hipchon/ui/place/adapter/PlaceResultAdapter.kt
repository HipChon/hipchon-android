package com.gritbus.hipchon.ui.place.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ItemPlaceResultBinding
import com.gritbus.hipchon.domain.model.PlaceData

class PlaceResultAdapter(
    private val fragmentManager: FragmentManager,
    private val lifecycle: Lifecycle,
    private val detailClickCallback: (PlaceData) -> (Unit),
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
        holder.bind(
            fragmentManager,
            lifecycle,
            currentList[position],
            detailClickCallback,
            saveClickCallback
        )
    }

    class PlaceResultViewHolder(
        private val binding: ItemPlaceResultBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            fragmentManager: FragmentManager,
            lifecycle: Lifecycle,
            placeData: PlaceData,
            detailClickCallback: (PlaceData) -> (Unit),
            saveClickCallback: (PlaceData) -> (Unit)
        ) {
            binding.root.setOnClickListener {
                detailClickCallback(placeData)
            }
            binding.vpHomePlaceThumbnail.adapter =
                PlaceResultImageAdapter(placeData.thumbnail, fragmentManager, lifecycle)
            binding.ivHomePlaceSaveCount.setOnClickListener {
                saveClickCallback(placeData)
            }
            binding.ivHomePlaceSaveCount.background = when (placeData.isSave) {
                true -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_save_filled)
                false -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_save)
            }
            binding.tvHomePlaceTitle.text = placeData.title
            binding.tvHomePlaceSaveCount.text = placeData.saveCount.toString()
            binding.tvHomePlaceFeedCount.text = placeData.feedCount.toString()

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
