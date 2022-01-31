package ru.fefu.activitytracker.Items

data class ActivityMapItem(
    val type: String?,
    val record: String?,
    var isSelected: Boolean = false
)