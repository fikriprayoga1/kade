package com.example.footballmatchschedule.other.jetpack

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

//@Dao
//interface UserDao {
//    @Insert(onConflict = REPLACE)
//    fun createUser(user: User)
//
//    // -----------------------------------------------------------------------------------------------------------------
//    @Query("SELECT * FROM user WHERE name = :name")
//    fun readUser(name: String) : LiveData<User>
//
//    // -----------------------------------------------------------------------------------------------------------------
//    @Query("DELETE FROM user WHERE name = :name")
//    fun deleteUser(name: String)
//
//}