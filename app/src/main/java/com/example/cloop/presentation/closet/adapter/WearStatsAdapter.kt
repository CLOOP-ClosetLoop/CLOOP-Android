package com.example.cloop.presentation.closet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cloop.data.model.cloth.WearStat
import com.example.cloop.databinding.ItemStatsClothBinding

class WearStatsAdapter : ListAdapter<WearStat, WearStatsAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemStatsClothBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WearStat) {
            binding.tvName.text = item.clothName
            binding.tvStats.text = "Worn ${item.wearCount} times"
            binding.tvLastWorn.text = "Last worn\n${item.lastWornAt}"
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
