package com.example.footballmatchschedule.other.jetpack

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.footballmatchschedule.model.database.LMEDatabase
import com.example.footballmatchschedule.model.database.LeagueDatabase
import com.example.footballmatchschedule.model.database.Note

@Dao
interface UserDao {
    @Query("SELECT * FROM LeagueDatabase")
    fun readLeagueList(): LiveData<List<LeagueDatabase>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLeague(league: LeagueDatabase)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note: Note)

    @Query("SELECT * FROM note WHERE name = :name")
    fun readNote(name: String): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLastMatchEvent(lastMatchEvent: LMEDatabase)

    @Query("SELECT * FROM LMEDatabase")
    fun readLastMatchEventList(): LiveData<List<LMEDatabase>>

    @Query("SELECT * FROM lmedatabase WHERE id = :id")
    fun readLastMatchEventList2(id: String): List<LMEDatabase>
}