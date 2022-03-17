package com.gritbus.hipchon.ui.feed.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.gritbus.hipchon.R
import com.gritbus.hipchon.databinding.ItemFeedCreateKeywordBinding

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
            // TODO 리스트 const로 빼고 동적 뷰 추가하는 것도 함수 분리하기 필수..
            binding.tvKeywordTitle.text = data
            val chipList = when (data) {
                "시설" -> {
                    listOf("화장실이 청결해요", "주차하기 용이해요", "매장이 넓어요", "좌석이 편해요", "인터넷 접속이 원활해요")
                }
                "분위기" -> {
                    listOf("인테리어가 멋있어요", "주변 경관이 좋아요", "사진 찍기 좋아요", "단체 모임 하기 좋아요", "작업하기 좋아요")
                }
                else -> {
                    listOf("직원분들이 친절해요", "음식이 맛있어요", "체험활동이 재밌어요", "아이들이 좋아해요", "여자/남자친구가 좋아해요")
                }
            }
            chipList.forEach {
                val chip =
                    Chip(binding.root.context).apply {
                        elevation = binding.root.context.resources.getDimension(R.dimen.spacing_4)
                        chipBackgroundColor = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.white
                            )
                        )
                        chipStrokeWidth = 0f
                        chipIcon =
                            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_water)
                        chipIconSize =
                            binding.root.context.resources.getDimension(R.dimen.spacing_20)
                        text = it
                        setOnClickListener {
                            chipBackgroundColor = when (data) {
                                "시설" -> {
                                    ColorStateList.valueOf(
                                        ContextCompat.getColor(
                                            binding.root.context,
                                            R.color.yellow_kakao_counseling
                                        )
                                    )
                                }
                                "분위기" -> {
                                    ColorStateList.valueOf(
                                        ContextCompat.getColor(
                                            binding.root.context,
                                            R.color.primary_green
                                        )
                                    )
                                }
                                else -> {
                                    ColorStateList.valueOf(
                                        ContextCompat.getColor(
                                            binding.root.context,
                                            R.color.secondary_blue
                                        )
                                    )
                                }
                            }
                        }
                    }
                binding.cgKeyword.addView(chip)

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
