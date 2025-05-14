package com.unity.mindgarden

import java.util.Date

data class DailyHistory(
    val userId: String = "",
    val documentId: String = "",
    val title: String,
    val content: String,
    val dateTime: Date,
    val label: String,
    val score: Double,
)
