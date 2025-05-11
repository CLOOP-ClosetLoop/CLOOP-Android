package com.example.cloop.data.model.cloth

data class ClothRegisterRequest(
    val clothName: String,
    val category: String,
    val brand: String,
    val purchasedAt: String,
    val color: String,
    val season: String,
    val imageUrl: String
)
