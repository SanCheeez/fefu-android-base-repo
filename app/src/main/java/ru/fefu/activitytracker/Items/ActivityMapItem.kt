package ru.fefu.activitytracker.Items

data class ActivityMapItem(
    val type: Int,
    val record: String?,
    var isSelected: Boolean = false
)