package com.example.footballmatchschedule.other.jetpack

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.footballmatchschedule.model.database.EventDatabase
import com.example.footballmatchschedule.model.database.TeamDatabase

@Dao
interface UserDao {
    // Event ---------------------------------------------------------------------------------------
    @Insert
    fun createEvent(eventDatabase: EventDatabase)

    @Query("SELECT * FROM eventDatabase WHERE isFavorite = 1")
    fun readFavoriteEvent(): LiveData<List<EventDatabase>>

    @Query("SELECT * FROM eventDatabase WHERE isAlarm IS NOT NULL")
    fun readAlarmEvent(): LiveData<List<EventDatabase>>

    @Query("SELECT * FROM eventDatabase WHERE idEvent = :idEvent")
    fun readEvent(idEvent: String): List<EventDatabase>

    @Update
    fun updateEvent(eventDatabase: EventDatabase)

    @Delete
    fun deleteEvent(eventDatabase: EventDatabase)

    // Team ----------------------------------------------------------------------------------------
    @Insert
    fun createTeam(teamDatabase: TeamDatabase)

    @Query("SELECT * FROM teamDatabase WHERE isFavorite = 1")
    fun readFavoriteTeam(): LiveData<List<TeamDatabase>>

    @Query("SELECT * FROM teamDatabase WHERE idTeam = :idTeam")
    fun readTeam(idTeam: String): List<TeamDatabase>

    @Update
    fun updateTeam(teamDatabase: TeamDatabase)

    @Delete
    fun deleteTeam(teamDatabase: TeamDatabase)

}