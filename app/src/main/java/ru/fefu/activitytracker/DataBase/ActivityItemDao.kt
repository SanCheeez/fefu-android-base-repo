package ru.fefu.activitytracker.DataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ActivityItemDao {
    @Query("SELECT * FROM activities ORDER BY date_start DESC")
    fun getAll(): LiveData<List<ActivityItemEntity>>

    @Insert
    fun insertActivity(vararg ActivityItemEntity: ActivityItemEntity)
}