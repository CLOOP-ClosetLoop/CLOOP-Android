package com.example.cloop.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cloop.R
import com.example.cloop.data.model.cloth.Cloth
import com.example.cloop.databinding.ItemSelectedClothBinding
import com.example.cloop.presentation.home.viewmodel.OutfitRegisterViewModel

class ClothSelectAdapter(
    private val clothes: List<Cloth>,
    private val viewModel: OutfitRegisterViewModel
) : RecyclerView.Adapter<ClothSelectAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemSelectedClothBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Cloth) {
            binding.tvName.text = item.clothName
            binding.tvCategory.text = item.category
            binding.tvBrand.text = item.brand
            binding.tvColor.text = item.color
            binding.tvSeason.text = item.season

            Glide.with(binding.ivCloth.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.ic_placeholder_image)
                .into(binding.ivCloth)

            val isSelected = viewModel.isClothSelected(item.clothId)
            val color = if (isSelected) {
                ContextCompat.getColor(binding.root.context, R.color.primary_2)
            } else {
                ContextCompat.getColor(binding.root.context, R.color.white)
            }
            binding.cardView.setCardBackgroundColor(color)

            binding.cardView.setOnClickListener {
                viewModel.toggleClothSelection(item)
                notifyItemChanged(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSelectedClothBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = clothes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clothes[position])
    }
}

