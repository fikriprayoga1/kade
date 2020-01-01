package com.example.footballmatchschedule.other.jetpack

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.footballmatchschedule.model.database.EventDatabase
import com.example.footballmatchschedule.model.database.TeamDatabase

@Dao
interface UserDao {
    // Event ---------------------------------------------------------------------------------------
    @Insert(onConflict = REPLACE)
    fun createEvent(eventDatabase: EventDatabase)

    @Query("SELECT * FROM eventDatabase WHERE isFavorite = 1")
    fun readFavoriteEvent() : LiveData<List<EventDatabase>>

    @Query("SELECT * FROM eventDatabase WHERE isAlarm IS NOT NULL")
    fun readAlarmEvent() : LiveData<List<EventDatabase>>

    @Query("SELECT * FROM eventDatabase WHERE idEvent = :idEvent")
    fun readEvent(idEvent: String) : List<EventDatabase>

    @Query("DELETE FROM eventDatabase WHERE idEvent = :idEvent")
    fun deleteEvent(idEvent: String)

    // Team ----------------------------------------------------------------------------------------
    @Insert(onConflict = REPLACE)
    fun createTeam(teamDatabase: TeamDatabase)

    @Query("SELECT * FROM teamDatabase WHERE isFavorite = 1")
    fun readFavoriteTeam() : LiveData<List<TeamDatabase>>

    @Query("SELECT * FROM teamDatabase WHERE idTeam = :idTeam")
    fun readTeam(idTeam: String) : List<TeamDatabase>

    @Query("DELETE FROM teamDatabase WHERE idTeam = :idTeam")
    fun deleteTeam(idTeam: String)

}