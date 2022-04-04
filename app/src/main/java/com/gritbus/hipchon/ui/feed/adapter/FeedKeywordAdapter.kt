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

class FeedKeywordAdapter(
    private val keywordClickListener: (List<Int>) -> (Unit)
) : ListAdapter<String, FeedKeywordAdapter.FeedKeywordViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedKeywordViewHolder {
        return FeedKeywordViewHolder(
            ItemFeedCreateKeywordBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FeedKeywordViewHolder, position: Int) {
        holder.bind(currentList[position], keywordClickListener)
    }

    class FeedKeywordViewHolder(
        private val binding: ItemFeedCreateKeywordBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val checkedKeywordList = mutableListOf<Int>()

        fun bind(data: String, keywordClickListener: (List<Int>) -> (Unit)) {
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
            setKeyword(keywordList, keywordClickListener)
        }

        private fun setKeyword(keywordList: Array<*>, keywordClickListener: (List<Int>) -> (Unit)) {
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
                    getKeywordData(keyword)?.let { iconAndContentAndColorWithId ->
                        keywordBinding?.ivFeedCreateKeyword?.setImageResource(
                            iconAndContentAndColorWithId.first.first
                        )
                        keywordBinding?.tvFeedCreateKeyword?.text =
                            binding.root.context.resources.getString(iconAndContentAndColorWithId.first.second)
                        setClickListener(
                            keywordView,
                            iconAndContentAndColorWithId.first.third,
                            iconAndContentAndColorWithId.second,
                            keywordClickListener
                        )
                    }
                    binding.llKeyword.addView(keywordView)
                }
            }
        }

        private fun setClickListener(
            keywordView: ConstraintLayout,
            color: Int,
            keywordId: Int,
            keywordClickListener: (List<Int>) -> (Unit)
        ) {
            keywordView.setOnClickListener {
                if (keywordId in checkedKeywordList) {
                    keywordView.backgroundTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.white
                            )
                        )
                    checkedKeywordList.remove(keywordId)
                } else {
                    keywordView.backgroundTintList =
                        ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, color))
                    checkedKeywordList.add(keywordId)
                }
                keywordClickListener(checkedKeywordList)
            }
        }

        private fun getKeywordData(keyword: Any): Pair<Triple<Int, Int, Int>, Int>? {
            return when (keyword) {
                is KeywordFacility -> {
                    Triple(
                        keyword.iconDrawable,
                        keyword.contentString,
                        R.color.secondary_yellow
                    ) to keyword.ordinal + 1
                }
                is KeywordMood -> {
                    Triple(
                        keyword.iconDrawable,
                        keyword.contentString,
                        R.color.primary_green
                    ) to keyword.ordinal + 6
                }
                is KeywordSatisfaction -> {
                    Triple(
                        keyword.iconDrawable,
                        keyword.contentString,
                        R.color.secondary_blue
                    ) to keyword.ordinal + 11
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
