package ru.fefu.activitytracker.stores

import ru.fefu.activitytracker.Items.ActivityItem

class MyActivityStore {
    private val myStore = listOf<ActivityItem>(
        ActivityItem(
            "14.32 км",
            "2 часа 46 минут",
            "Сёрфинг",
            "",
            "14 часов назад",
            "Старт 12:00 | Финиш 14:46"
        ),
        ActivityItem(
            "1000 м",
            "60 минут",
            "Велосипед",
            "",
            "29.05.2022",
            "Старт 12:00 | Финиш 14:46"
        ),
    )

    fun getStore(): List<ActivityItem> = myStore
}