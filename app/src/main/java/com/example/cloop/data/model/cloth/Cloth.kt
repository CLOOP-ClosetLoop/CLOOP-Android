package com.example.cloop.data.model.cloth

data class Cloth(
    val clothId: Int,
    val clothName: String,
    val category: String,
    val brand: String,
    val purchasedAt: String,
    val color: String,
    val season: String,
    val donated: Boolean,
    val imageUrl: String,
    val lastWornAt: String
)