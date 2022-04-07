package com.gritbus.hipchon.ui.home.adapter

import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gritbus.hipchon.R
import com.gritbus.hipchon.data.model.place.LocalHipsterPlace
import com.gritbus.hipchon.data.model.place.LocalHipsterPost
import com.gritbus.hipchon.databinding.ItemLocalHipsterPickBinding
import com.gritbus.hipchon.databinding.ItemLocalHipsterPickHeaderBinding

class LocalHipsterAdapter(
    private val clickListener: (Int) -> (Unit),
    private val saveClickListener: (LocalHipsterPlace) -> Unit,
    private val fragmentManager: FragmentManager,
    private val lifecycle: Lifecycle
) : ListAdapter<LocalHipsterPost, RecyclerView.ViewHolder>(diffUtil) {

    private val HEADER = 0
    private val CONTENT = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER -> {
                LocalHipsterHeaderViewHolder(
                    ItemLocalHipsterPickHeaderBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
            else -> {
                LocalHipsterViewHolder(
                    ItemLocalHipsterPickBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LocalHipsterViewHolder -> holder.bind(
                currentList[position],
                clickListener,
                saveClickListener,
                fragmentManager,
                lifecycle
            )
            is LocalHipsterHeaderViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HEADER
            else -> CONTENT
        }
    }

    class LocalHipsterHeaderViewHolder(
        private val binding: ItemLocalHipsterPickHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(localHipsterPost: LocalHipsterPost) {
            binding.tvLocalHipsterPickHeader.text = localHipsterPost.title
        }
    }

    class LocalHipsterViewHolder(
        private val binding: ItemLocalHipsterPickBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            localHipsterPost: LocalHipsterPost,
            clickListener: (Int) -> (Unit),
            saveClickListener: (LocalHipsterPlace) -> (Unit),
            fragmentManager: FragmentManager,
            lifecycle: Lifecycle
        ) {
            binding.vpLocalHipsterPickThumb.adapter =
                LocalHipsterThumbAdapter(
                    localHipsterPost.imageList,
                    fragmentManager,
                    lifecycle
                )
            binding.tvLocalHipsterPickTitle.text = localHipsterPost.title
            binding.tvLocalHipsterPickDesc.text = localHipsterPost.detail
            binding.tvLocalHipsterPickPlaceTitle.text = localHipsterPost.place.name
            binding.tvLocalHipsterPickPlaceAddress.text =
                "${localHipsterPost.place.category} • ${localHipsterPost.place.address}"

            binding.ivLocalHipsterPickPlaceSave.background = when (localHipsterPost.place.isMyplace) {
                true -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_save_filled)
                false -> ContextCompat.getDrawable(binding.root.context, R.drawable.ic_save)
            }
            binding.ivLocalHipsterPickPlaceSave.backgroundTintList =when (localHipsterPost.place.isMyplace) {
                true -> ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.primary_green))
                false -> ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.black))
            }

            binding.clLocalHipsterPickPlace.setOnClickListener {
                clickListener(localHipsterPost.place.placeId)
            }
            binding.ivLocalHipsterPickPlaceSave.setOnClickListener {
                saveClickListener(localHipsterPost.place)
            }
            binding.ivLocalHipsterPickPlaceShare.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, localHipsterPost.place.homepage)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(binding.root.context, shareIntent, null)
            }
            binding.executePendingBindings()
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<LocalHipsterPost>() {
            override fun areContentsTheSame(
                oldItem: LocalHipsterPost,
                newItem: LocalHipsterPost
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: LocalHipsterPost,
                newItem: LocalHipsterPost
            ): Boolean {
                return oldItem.hipsterPostId == newItem.hipsterPostId
            }
        }
    }
}
