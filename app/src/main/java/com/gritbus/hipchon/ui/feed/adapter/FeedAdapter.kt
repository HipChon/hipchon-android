package com.gritbus.hipchon.ui.feed.adapter

import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gritbus.hipchon.R
import com.gritbus.hipchon.data.model.feed.FeedAllDataItem
import com.gritbus.hipchon.data.model.feed.FeedPlaceItem
import com.gritbus.hipchon.databinding.ItemFeedPreviewBinding
import com.gritbus.hipchon.utils.ItemDecorationWithHorizontalSpacing
import com.gritbus.hipchon.utils.dateToFormattedString

class FeedAdapter(
    private val isPlaceDetail: Boolean,
    private val clickListener: (FeedAllDataItem) -> (Unit),
    private val moveToPlaceDetail: (Int) -> (Unit),
    private val savePlace: (FeedPlaceItem) -> (Unit)
) : ListAdapter<FeedAllDataItem, FeedAdapter.FeedViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(
            ItemFeedPreviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(
            currentList[position],
            isPlaceDetail,
            clickListener,
            moveToPlaceDetail,
            savePlace
        )
    }

    class FeedViewHolder(
        private val binding: ItemFeedPreviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val feedThumbAdapter = FeedThumbAdapter(false)

        init {
            binding.rvFeedPreview.adapter = feedThumbAdapter
            binding.rvFeedPreview.addItemDecoration(ItemDecorationWithHorizontalSpacing(4))
        }

        fun bind(
            feedData: FeedAllDataItem,
            isPlaceDetail: Boolean,
            clickListener: (FeedAllDataItem) -> Unit,
            moveToPlaceDetail: (Int) -> (Unit),
            savePlace: (FeedPlaceItem) -> (Unit)
        ) {
            binding.root.setOnClickListener {
                clickListener(feedData)
            }

            Glide.with(binding.root.context)
                .load(feedData.user.image)
                .error(R.drawable.ic_profile_default)
                .into(binding.ivFeedPreviewProfile)
            binding.tvFeedPreviewNickname.text = feedData.user.name
            binding.tvFeedPreviewReviewCount.text = "${feedData.user.postCnt}번째 리뷰"
            binding.tvFeedPreviewCreatedAt.text = feedData.time.dateToFormattedString()
            binding.tvFeedPreviewFavorite.text = feedData.likeCnt.toString()
            binding.tvFeedPreviewComment.text = feedData.commentCnt.toString()
            binding.rmtvFeedPreviewContent.text = feedData.detail
            feedData.imageList?.let {
                feedThumbAdapter.submitList(it.filter { it != null })
            }

            if (isPlaceDetail) {
                binding.clFeedPreviewPlace.visibility = View.GONE
            }
            binding.tvFeedPreviewPlaceTitle.text = feedData.place.name
            binding.tvFeedPreviewPlaceAddress.text =
                "${feedData.place.category} • ${feedData.place.address}"

            binding.clFeedPreviewPlace.setOnClickListener {
                moveToPlaceDetail(feedData.place.placeId)
            }
            binding.ivFeedPreviewPlaceShare.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, feedData.place.homepage)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                ContextCompat.startActivity(binding.root.context, shareIntent, null)
            }
            setSaveView(feedData.place.isMyplace)
            binding.ivFeedPreviewPlaceSave.setOnClickListener {
                savePlace(feedData.place)
            }
            binding.executePendingBindings()
        }

        private fun setSaveView(isMyplace: Boolean) {
            binding.ivFeedPreviewPlaceSave.background = when (isMyplace) {
                true -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_save_filled)
                false -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_save)
            }
            binding.ivFeedPreviewPlaceSave.backgroundTintList =when (isMyplace) {
                true -> ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.primary_green))
                false -> ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.black))
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<FeedAllDataItem>() {
            override fun areItemsTheSame(
                oldItem: FeedAllDataItem,
                newItem: FeedAllDataItem
            ): Boolean {
                return oldItem.postId == newItem.postId
            }

            override fun areContentsTheSame(
                oldItem: FeedAllDataItem,
                newItem: FeedAllDataItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
