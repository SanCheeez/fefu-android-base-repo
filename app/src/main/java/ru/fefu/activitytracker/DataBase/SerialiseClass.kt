package ru.fefu.activitytracker.DataBase

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SerialiseClass {
    fun listEncode(list: List<Pair<Double, Double>>): String {
        return Json.encodeToString(list)
    }

    fun listDecode(list: String): List<Pair<Double, Double>> {
        return Json.decodeFromString(list)
    }

    fun itemEncode(activity_item: Activity): String {
        return Json.encodeToString(activity_item)
    }

    fun itemDecode(activity_item: String): ActivityItemEntity {
        return Json.decodeFromString(activity_item)
    }
}