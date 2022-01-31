package ru.fefu.activitytracker.stores

import ru.fefu.activitytracker.DataBase.ActivityType
import ru.fefu.activitytracker.Items.ActivityItem
import ru.fefu.activitytracker.Items.ActivityMapItem

class MapActivityStore {
    private val mapStore = listOf<ActivityMapItem>(
        ActivityMapItem(
            ActivityType.WALKING.ordinal,
            "14.32 км",
        ),
        ActivityMapItem(
            ActivityType.BICYCLE.ordinal,
            "1 км",
        ),
        ActivityMapItem(
            ActivityType.RUNNING.ordinal,
            "10 км",
        ),
    )

    fun getStore(): List<ActivityMapItem> = mapStore
}