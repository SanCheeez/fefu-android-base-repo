package ru.fefu.activitytracker.stores

import ru.fefu.activitytracker.Items.ActivityItem
import ru.fefu.activitytracker.Items.ActivityMapItem

class MapActivityStore {
    private val mapStore = listOf<ActivityMapItem>(
        ActivityMapItem(
            "Сёрфинг",
            "14.32 км",
        ),
        ActivityMapItem(
            "Велосипед",
            "1 км",
        ),
        ActivityMapItem(
            "Бег",
            "10 км",
        ),
    )

    fun getStore(): List<ActivityMapItem> = mapStore
}