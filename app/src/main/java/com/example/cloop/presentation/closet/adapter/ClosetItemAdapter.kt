package com.example.cloop.presentation.closet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cloop.R
import com.example.cloop.data.model.cloth.Cloth
import com.example.cloop.databinding.ItemClothBinding

class ClosetItemAdapter : ListAdapter<Cloth, ClosetItemAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemClothBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cloth: Cloth) {
            binding.tvName.text = cloth.clothName
            binding.tvCategory.text = cloth.category
            binding.tvBrand.text = cloth.brand
            binding.tvDate.text = "${cloth.purchasedAt} 구매"
            binding.tvColor.text = cloth.color
            binding.tvSeason.text = cloth.season
            Glide.with(binding.ivCloth)
                .load(cloth.imageUrl)
                .placeholder(R.drawable.ic_placeholder_image)
                .into(binding.ivCloth)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemClothBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Cloth>() {
        override fun areItemsTheSame(oldItem: Cloth, newItem: Cloth) = oldItem.clothId == newItem.clothId
        override fun areContentsTheSame(oldItem: Cloth, newItem: Cloth) = oldItem == newItem
    }
}
