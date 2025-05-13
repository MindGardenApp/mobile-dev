package com.unity.mindgarden

import java.util.Date

data class DailyHistory(
    val title: String,
    val diary: String,
    val label: String,
    val dateTime: Date
)
