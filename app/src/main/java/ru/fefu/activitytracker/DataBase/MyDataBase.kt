package ru.fefu.activitytracker.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ActivityItemEntity::class], version = 1)
abstract class MyDataBase : RoomDatabase() {
    abstract fun activityDao(): ActivityItemDao
}