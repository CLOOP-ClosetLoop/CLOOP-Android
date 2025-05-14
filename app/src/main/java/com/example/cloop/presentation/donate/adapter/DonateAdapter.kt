package com.example.cloop.presentation.donate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cloop.R
import com.example.cloop.data.model.donate.DonationCloth
import com.example.cloop.databinding.ItemDonationBinding

class DonateAdapter(
    private val onItemClick: (DonationCloth) -> Unit
) : RecyclerView.Adapter<DonateAdapter.ViewHolder>() {

    private var items = listOf<DonationCloth>()

    fun submitList(list: List<DonationCloth>) {
        items = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemDonationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DonationCloth) {
            binding.tvName.text = item.clothName
            binding.tvLastWorn.text = "${item.lastWornAt} \nLast worn"
            Glide.with(binding.ivCloth.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.ic_placeholder_image)
                .into(binding.ivCloth)

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDonationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}