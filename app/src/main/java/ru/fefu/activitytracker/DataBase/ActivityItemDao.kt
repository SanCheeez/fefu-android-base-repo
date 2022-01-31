package ru.fefu.activitytracker.DataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ActivityItemDao {
    @Query("SELECT * FROM activities ORDER BY date_start DESC")
    fun getAll(): LiveData<List<ActivityItemEntity>>

    @Query("SELECT * FROM activities WHERE id = (SELECT MAX(id) FROM activities)")
    fun getLast(): ActivityItemEntity?

    @Query("SELECT * FROM activities WHERE id = (SELECT MAX(id) FROM activities)")
    fun getLastInTime(): LiveData<ActivityItemEntity>

    @Query("SELECT MAX(id) FROM activities")
    fun getLastId(): Int

    @Query("SELECT * FROM activities WHERE id = :id")
    fun getById(id:Int): ActivityItemEntity

    @Update
    fun updateLast(vararg ActivityItemEntity: ActivityItemEntity)

    @Insert
    fun insertActivity(vararg ActivityItemEntity: ActivityItemEntity)
}