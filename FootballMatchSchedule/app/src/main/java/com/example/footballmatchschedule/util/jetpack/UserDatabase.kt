package com.example.footballmatchschedule.util.jetpack

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.footballmatchschedule.model.database.TeamDatabase
import com.example.footballmatchschedule.model.database.EventDatabase

@Database(entities = [EventDatabase::class, TeamDatabase::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, UserDatabase::class.java, "football_match_schedule")
                .build()
    }

}