package com.example.cloop.presentation.closet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cloop.R
import com.example.cloop.data.model.cloth.WearStat
import com.example.cloop.databinding.ItemStatsClothBinding

class WearStatsAdapter : ListAdapter<WearStat, WearStatsAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemStatsClothBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WearStat) {
            binding.tvName.text = item.clothName
            binding.tvStats.text = "Worn ${item.wearCount} times"
            binding.tvLastWorn.text = "Last worn\n${item.lastWornAt}"

            Glide.with(binding.ivCloth.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.ic_placeholder_image)
                .into(binding.ivCloth)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStatsClothBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<WearStat>() {
        override fun areItemsTheSame(oldItem: WearStat, newItem: WearStat) = oldItem.clothId == newItem.clothId
        override fun areContentsTheSame(oldItem: WearStat, newItem: WearStat) = oldItem == newItem
    }
}
