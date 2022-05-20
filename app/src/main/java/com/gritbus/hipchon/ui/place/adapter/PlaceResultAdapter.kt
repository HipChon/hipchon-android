package com.gritbus.hipchon.ui.place.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gritbus.hipchon.R
import com.gritbus.hipchon.data.model.place.KeywordItem
import com.gritbus.hipchon.data.model.place.PlaceSearchAllDataItem
import com.gritbus.hipchon.databinding.ItemPlaceResultBinding
import com.gritbus.hipchon.domain.model.Keyword
import com.gritbus.hipchon.domain.model.KeywordFacility
import com.gritbus.hipchon.domain.model.KeywordMood
import com.gritbus.hipchon.domain.model.KeywordSatisfaction

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

        private var isViewpagerClick = false

        fun bind(
            fragmentManager: FragmentManager,
            lifecycle: Lifecycle,
            placeData: PlaceSearchAllDataItem,
            detailClickCallback: (Int) -> (Unit)
        ) {
            binding.root.setOnClickListener {
                detailClickCallback(placeData.placeId)
            }

            setAdapter(placeData, fragmentManager, lifecycle, detailClickCallback)
            setTitleSpannable(placeData)

            binding.tvHomePlaceSaveCount.text = placeData.myplaceCnt.toString()
            binding.tvHomePlaceFeedCount.text = placeData.postCnt.toString()
            placeData.keyword?.let { setKeyword(it) }

            binding.executePendingBindings()
        }

        @SuppressLint("ClickableViewAccessibility")
        private fun setAdapter(
            placeData: PlaceSearchAllDataItem,
            fragmentManager: FragmentManager,
            lifecycle: Lifecycle,
            detailClickCallback: (Int) -> Unit
        ) {
            binding.vpHomePlaceThumbnail.adapter =
                PlaceResultImageAdapter(placeData.imageList, fragmentManager, lifecycle)

            binding.vpHomePlaceThumbnail.getChildAt(binding.vpHomePlaceThumbnail.currentItem)
                .setOnTouchListener { v, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            isViewpagerClick = true
                        }
                        MotionEvent.ACTION_UP -> {
                            if (isViewpagerClick) {
                                detailClickCallback(placeData.placeId)
                            }
                            isViewpagerClick = false

                        }
                        MotionEvent.ACTION_MOVE -> {
                            isViewpagerClick = false
                        }
                    }
                    false
                }
        }

        private fun setKeyword(keyword: KeywordItem) {
            binding.tvHomePlaceKeyword.text = keyword.keyword
            val backgroundTint = when (keyword.category) {
                Keyword.FACILITY.value -> {
                    R.color.secondary_yellow
                }
                Keyword.SATISFACTION.value -> {
                    R.color.primary_green
                }
                else -> {
                    R.color.secondary_blue
                }
            }
            when (keyword.keywordId) {
                in (1..5) -> {
                    binding.ivHomePlaceKeyword.setImageResource(KeywordFacility.values()[keyword.keywordId - 1].iconDrawable)
                }
                in (6..10) -> {
                    binding.ivHomePlaceKeyword.setImageResource(KeywordMood.values()[keyword.keywordId - 5 - 1].iconDrawable)
                }
                else -> {
                    binding.ivHomePlaceKeyword.setImageResource(KeywordSatisfaction.values()[keyword.keywordId - 10 - 1].iconDrawable)
                }
            }
            binding.llHomePlaceKeyword.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, backgroundTint))
        }

        private fun setTitleSpannable(placeData: PlaceSearchAllDataItem) {
            val title = placeData.name + "   " + placeData.city
            val spannableBuilder = SpannableStringBuilder(title)
            val colorGray =
                ForegroundColorSpan(ContextCompat.getColor(binding.root.context, R.color.gray04))
            val size16Span = RelativeSizeSpan(8f / 9f)
            spannableBuilder.setSpan(
                colorGray,
                placeData.name.length,
                title.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableBuilder.setSpan(
                size16Span,
                placeData.name.length,
                title.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            binding.tvHomePlaceTitle.text = spannableBuilder
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PlaceSearchAllDataItem>() {
            override fun areItemsTheSame(
                oldItem: PlaceSearchAllDataItem,
                newItem: PlaceSearchAllDataItem
            ): Boolean {
                return oldItem.placeId == newItem.placeId
            }

            override fun areContentsTheSame(
                oldItem: PlaceSearchAllDataItem,
                newItem: PlaceSearchAllDataItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
