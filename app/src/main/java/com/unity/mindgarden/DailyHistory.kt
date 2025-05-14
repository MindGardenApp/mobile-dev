package com.unity.mindgarden

import java.util.Date

data class DailyHistory(
    val title: String,
    val content: String,
    val dateTime: Date,
    val label: String,
    val score: Int
)
