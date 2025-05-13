package com.unity.mindgarden

data class TreeState(
    val stageName: String,
    val thresholds: List<IntRange>,
    val images: List<Int>,
    val statusLabels: List<String>
)