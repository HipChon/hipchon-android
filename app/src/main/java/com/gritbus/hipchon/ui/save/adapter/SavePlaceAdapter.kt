package com.gritbus.hipchon.ui.save.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.gritbus.hipchon.R
import com.gritbus.hipchon.data.model.my.MyPlaceAllDataItem
import com.gritbus.hipchon.databinding.ItemSavePlaceBinding

class SavePlaceAdapter :
    ListAdapter<MyPlaceAllDataItem, SavePlaceAdapter.SavePlaceViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavePlaceViewHolder {
        return SavePlaceViewHolder(
            ItemSavePlaceBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SavePlaceViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class SavePlaceViewHolder(
        private val binding: ItemSavePlaceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var isOpenAddress = false

        fun bind(myPlaceAllDataItem: MyPlaceAllDataItem) {
            binding.tvSavePlaceTitle.text = myPlaceAllDataItem.name
            binding.tvSavePlaceType.text = myPlaceAllDataItem.category
            binding.tvSavePlaceAddress.text = myPlaceAllDataItem.address
            binding.tvSavePlaceAddressFull.text = myPlaceAllDataItem.address
            if (hasAddressEllipsis(myPlaceAllDataItem.address)) {
                binding.ivSavePlaceAddress.visibility = View.VISIBLE
            } else {
                binding.ivSavePlaceAddress.visibility = View.INVISIBLE
            }
            binding.ivSavePlaceAddress.setOnClickListener {
                isOpenAddress = when (isOpenAddress) {
                    true -> {
                        controlPopup(true)
                        false
                    }
                    false -> {
                        controlPopup(false)
                        true
                    }
                }
            }
            binding.tvSavePlaceSaveCount.text = myPlaceAllDataItem.myplaceCnt.toString()
            binding.tvSavePlaceFeedCount.text = myPlaceAllDataItem.postCnt.toString()
            Glide.with(binding.root.context)
                .load(myPlaceAllDataItem.image)
                .transform(RoundedCorners(2))
                .into(binding.ivSavePlaceThumb)
            binding.tvSavePlaceMemo.text = myPlaceAllDataItem.memo
            binding.executePendingBindings()
        }

        private fun hasAddressEllipsis(address: String): Boolean {
            return binding.tvSavePlaceAddress.post {
                !binding.tvSavePlaceAddress.layout.text.toString().equals(address)
            }
        }

        private fun controlPopup(isOpen: Boolean) {
            when (isOpen) {
                true -> {
                    binding.ivSavePlaceAddress.setImageResource(R.drawable.ic_chevron_up)
                    binding.tvSavePlaceAddressFull.visibility = View.INVISIBLE
                }
                false -> {
                    binding.ivSavePlaceAddress.setImageResource(R.drawable.ic_chevron_down)
                    binding.tvSavePlaceAddressFull.visibility = View.VISIBLE
                }
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MyPlaceAllDataItem>() {
            override fun areItemsTheSame(
                oldItem: MyPlaceAllDataItem,
                newItem: MyPlaceAllDataItem
            ): Boolean {
                return oldItem.placeId == newItem.placeId
            }

            override fun areContentsTheSame(
                oldItem: MyPlaceAllDataItem,
                newItem: MyPlaceAllDataItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
