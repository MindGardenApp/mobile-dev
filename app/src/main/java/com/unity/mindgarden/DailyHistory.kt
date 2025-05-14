package com.unity.mindgarden

import java.util.Date
import com.google.firebase.Timestamp

data class DailyHistory(
    val title: String,
    val content: String,
    val dateTime: Timestamp,
    val label: String,
    val score: Double,
    val userId: String
)
