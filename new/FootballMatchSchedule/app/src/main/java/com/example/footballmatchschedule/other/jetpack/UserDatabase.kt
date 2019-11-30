package com.example.footballmatchschedule.other.jetpack

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.footballmatchschedule.model.database.LMEDatabase
import com.example.footballmatchschedule.model.database.LeagueDatabase
import com.example.footballmatchschedule.model.database.Note

@Database(entities = [LeagueDatabase::class, Note::class, LMEDatabase::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}