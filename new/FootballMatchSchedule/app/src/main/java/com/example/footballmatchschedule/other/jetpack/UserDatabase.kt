package com.example.footballmatchschedule.other.jetpack

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.footballmatchschedule.model.database.League

@Database(entities = [League::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}