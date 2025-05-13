package com.unity.mindgarden.model

data class PredictionResponse(
    val text: String,
    val label: String,
    val score: Double
)