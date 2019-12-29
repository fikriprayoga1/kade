package com.example.footballmatchschedule.other.jetpack

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.footballmatchschedule.model.apiresponse.EventDetail
import com.example.footballmatchschedule.model.apiresponse.TeamDetail

@Dao
interface UserDao {
    // Event ---------------------------------------------------------------------------------------
    @Insert(onConflict = REPLACE)
    fun createEvent(eventDetail: EventDetail)

    @Query("SELECT * FROM eventdetail WHERE isFavorite = 1")
    fun readFavoriteEvent() : LiveData<List<EventDetail>>

    @Query("SELECT * FROM eventdetail WHERE isAlarm IS NOT NULL")
    fun readAlarmEvent() : LiveData<List<EventDetail>>

    @Query("SELECT * FROM eventdetail WHERE idEvent = :idEvent")
    fun readEvent(idEvent: String) : List<EventDetail>

    @Query("DELETE FROM eventdetail WHERE idEvent = :idEvent")
    fun deleteEvent(idEvent: String)

    // Team ----------------------------------------------------------------------------------------
    @Insert(onConflict = REPLACE)
    fun createTeam(teamDetail: TeamDetail)

    @Query("SELECT * FROM teamdetail WHERE isFavorite = 1")
    fun readFavoriteTeam() : LiveData<List<TeamDetail>>

    @Query("SELECT * FROM teamdetail WHERE idTeam = :idTeam")
    fun readTeam(idTeam: String) : List<TeamDetail>

    @Query("DELETE FROM teamdetail WHERE idTeam = :idTeam")
    fun deleteTeam(idTeam: String)

}