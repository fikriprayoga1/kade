package com.example.footballmatchschedule.other.jetpack

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.footballmatchschedule.model.database.League

@Dao
interface UserDao {
    @Query("SELECT * FROM league")
    fun readLeagueList(): LiveData<List<League>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setLeagueList(league: League)
}