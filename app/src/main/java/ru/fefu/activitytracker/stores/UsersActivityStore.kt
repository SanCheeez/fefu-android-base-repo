package ru.fefu.activitytracker.stores

import ru.fefu.activitytracker.Items.ActivityItem

class UsersActivityStore {
    private val usersStore = listOf<ActivityItem>(
        ActivityItem(
            "14.32 км",
            "2 часа 46 минут",
            "Сёрфинг",
            "@van_darkholme",
            "14 часов назад",
            "Старт 12:00 | Финиш 14:46"
        ),
        ActivityItem(
            "228 м",
            "14 часов 48 минут",
            "Качели",
            "@techniquepasha",
            "14 часов назад",
            "Старт 12:00 | Финиш 14:46"
        ), ActivityItem(
            "10 км",
            "1 час 10 минут",
            "Езда на Кадиллак",
            "@morgen_shtern",
            "14 часов назад",
            "Старт 12:00 | Финиш 14:46"
        )
    )

    fun getStore(): List<ActivityItem> = usersStore
}