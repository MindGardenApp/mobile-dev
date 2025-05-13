package com.unity.mindgarden

import java.util.Date

data class WeeklyHistory(
    val dateStart: Date,
    val items: List<DailyHistory>,
)
