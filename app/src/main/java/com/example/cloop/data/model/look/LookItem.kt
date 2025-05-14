package com.example.cloop.data.model.look

import com.example.cloop.data.model.cloth.Cloth

data class LookItem(
    val lookId: Int,
    val createdAt: String,
    val imageUrl: String,
    val clothes: List<Cloth>
)