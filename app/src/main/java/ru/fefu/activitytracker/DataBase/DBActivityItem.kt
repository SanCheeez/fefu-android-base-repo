package ru.fefu.activitytracker.DataBase

import kotlinx.serialization.Serializable

sealed class DBActivityItem

@Serializable
class Activity(
    val id: Int?,
    val type: Int?,
    val date_start: Long?,
    val date_finish: Long?,
    val coordinates: String?,
) : DBActivityItem()

class Date_(
    val date: String?
) : DBActivityItem()