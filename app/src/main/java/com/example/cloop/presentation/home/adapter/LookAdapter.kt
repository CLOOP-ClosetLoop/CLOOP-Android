package com.example.cloop.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cloop.R
import com.example.cloop.data.model.cloth.Cloth
import com.example.cloop.databinding.ItemSelectedClothBinding

class LookAdapter : RecyclerView.Adapter<LookAdapter.ViewHolder>() {

    private val items = mutableListOf<Cloth>()

    fun submitList(newItems: List<Cloth>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemSelectedClothBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cloth: Cloth) {
            binding.tvName.text = cloth.clothName
            binding.tvCategory.text = cloth.category
            binding.tvBrand.text = cloth.brand
            binding.tvColor.text = cloth.color
            binding.tvSeason.text = cloth.season

            Glide.with(binding.ivCloth.context)
                .load(cloth.imageUrl)
                .placeholder(R.drawable.ic_placeholder_image)
                .into(binding.ivCloth)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSelectedClothBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

