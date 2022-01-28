package ru.fefu.activitytracker.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "activities")
@Serializable
data class ActivityItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "type") val type: Int? = null,
    @ColumnInfo(name = "date_start") val date_start: Long? = null,
    @ColumnInfo(name = "date_finish") val date_finish: Long? = null,
    @ColumnInfo(name = "coordinates") val coordinates: String? = null,
    @ColumnInfo(name = "date") val date: String? = null
)