package com.example.footballmatchschedule.other.jetpack

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.footballmatchschedule.model.apiresponse.EventDetail
import com.example.footballmatchschedule.model.apiresponse.TeamDetail

@Database(entities = [EventDetail::class, TeamDetail::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}