package com.gritbus.hipchon.ui.feed.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gritbus.hipchon.R
import com.gritbus.hipchon.data.model.feed.FeedAllDataItem
import com.gritbus.hipchon.databinding.ItemFeedPreviewBinding
import com.gritbus.hipchon.utils.ItemDecorationWithHorizontalSpacing

class FeedAdapter(
    private val isPlaceDetail: Boolean,
    private val clickListener: () -> (Unit)
) : ListAdapter<FeedAllDataItem, FeedAdapter.FeedViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(
            ItemFeedPreviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(currentList[position], isPlaceDetail, clickListener)
    }

    class FeedViewHolder(
        private val binding: ItemFeedPreviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val feedThumbAdapter = FeedThumbAdapter()

        init {
            binding.rvFeedPreview.adapter = feedThumbAdapter
            binding.rvFeedPreview.addItemDecoration(ItemDecorationWithHorizontalSpacing(4))
        }

        fun bind(feedData: FeedAllDataItem, isPlaceDetail: Boolean, clickListener: () -> Unit) {
            binding.root.setOnClickListener {
                clickListener()
            }
            if (isPlaceDetail) {
                binding.clFeedPreviewPlace.visibility = View.GONE
            }
            Glide.with(binding.root.context)
                .load(feedData.user.image)
                .error(R.drawable.ic_profile_default)
                .into(binding.ivFeedPreviewProfile)
            binding.tvFeedPreviewNickname.text = feedData.user.name
            binding.tvFeedPreviewReviewCount.text = "${feedData.user.postCnt}번째 리뷰"
            binding.tvFeedPreviewCreatedAt.text = feedData.time
            binding.tvFeedPreviewFavorite.text = feedData.likeCnt.toString()
            binding.tvFeedPreviewComment.text = feedData.commentCnt.toString()
            binding.rmtvFeedPreviewContent.text = feedData.detail
            feedData.imageList?.let {
                feedThumbAdapter.submitList(it.filter { it != null })
            }

            binding.tvFeedPreviewPlaceTitle.text = feedData.place.name
            binding.tvFeedPreviewPlaceAddress.text =
                "${feedData.place.category} • ${feedData.place.address}"
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
