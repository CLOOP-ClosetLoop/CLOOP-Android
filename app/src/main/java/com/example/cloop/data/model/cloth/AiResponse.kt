package com.example.cloop.data.model.cloth

data class AiResponse(
    val predictedName: String,
    val predictedCategory: String,
    val predictedColor: String,
    val predictedSeason: String,
    val confidence: Double
)