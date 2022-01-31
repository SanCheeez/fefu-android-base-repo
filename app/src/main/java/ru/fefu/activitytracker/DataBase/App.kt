package ru.fefu.activitytracker.DataBase

import android.app.Application
import androidx.room.Room

class App : Application() {
    companion object {
        lateinit var INSTANCE: App
    }

    val db: MyDataBase by lazy {
        Room.databaseBuilder(this, MyDataBase::class.java, "my_database")
            .allowMainThreadQueries().build()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}