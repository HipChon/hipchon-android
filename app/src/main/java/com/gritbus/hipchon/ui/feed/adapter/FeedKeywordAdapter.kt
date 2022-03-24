package com.gritbus.hipchon.ui.feed.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ItemFeedCreateKeywordBinding
import com.gritbus.hipchon.databinding.ItemFeedCreateKeywordSelectorBinding
import com.gritbus.hipchon.domain.model.Keyword
import com.gritbus.hipchon.domain.model.KeywordFacility
import com.gritbus.hipchon.domain.model.KeywordMood
import com.gritbus.hipchon.domain.model.KeywordSatisfaction

class FeedKeywordAdapter : ListAdapter<String, FeedKeywordAdapter.FeedKeywordViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedKeywordViewHolder {
        return FeedKeywordViewHolder(
            ItemFeedCreateKeywordBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FeedKeywordViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class FeedKeywordViewHolder(
        private val binding: ItemFeedCreateKeywordBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: String) {
            binding.tvKeywordTitle.text = data
            val keywordList = when (data) {
                Keyword.FACILITY.value -> {
                    Keyword.FACILITY.keywordAll as Array<*>
                }
                Keyword.MOOD.value -> {
                    Keyword.MOOD.keywordAll as Array<*>
                }
                Keyword.SATISFACTION.value -> {
                    Keyword.SATISFACTION.keywordAll as Array<*>
                }
                else -> {
                    return
                }
            }
            setKeyword(keywordList)
        }

        private fun setKeyword(keywordList: Array<*>) {
            keywordList.forEach { keyword ->
                val keywordView = LayoutInflater.from(binding.root.context)
                    .inflate(
                        R.layout.item_feed_create_keyword_selector,
                        binding.llKeyword,
                        false
                    ) as ConstraintLayout

                val keywordBinding =
                    DataBindingUtil.bind<ItemFeedCreateKeywordSelectorBinding>(keywordView)
                if (keyword != null) {
                    getKeywordData(keyword)?.let { iconAndContentAndColor ->
                        keywordBinding?.ivFeedCreateKeyword?.setImageResource(iconAndContentAndColor.first)
                        keywordBinding?.tvFeedCreateKeyword?.text =
                            binding.root.context.resources.getString(iconAndContentAndColor.second)
                        setClickListener(keywordView, iconAndContentAndColor.third)
                    }
                    binding.llKeyword.addView(keywordView)
                }
            }
        }

        private fun setClickListener(keywordView: ConstraintLayout, color: Int) {
            keywordView.setOnClickListener {
                keywordView.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, color))
            }
        }

        private fun getKeywordData(keyword: Any): Triple<Int, Int, Int>? {
            return when (keyword) {
                is KeywordFacility -> {
                    Triple(
                        keyword.iconDrawable,
                        keyword.contentString,
                        R.color.secondary_yellow
                    )
                }
                is KeywordMood -> {
                    Triple(
                        keyword.iconDrawable,
                        keyword.contentString,
                        R.color.primary_green
                    )
                }
                is KeywordSatisfaction -> {
                    Triple(
                        keyword.iconDrawable,
                        keyword.contentString,
                        R.color.secondary_blue
                    )
                }
                else -> {
                    null
                }
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}
