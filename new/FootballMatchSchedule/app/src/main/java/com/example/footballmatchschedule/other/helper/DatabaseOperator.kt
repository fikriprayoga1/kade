package com.example.footballmatchschedule.other.helper

import androidx.lifecycle.LiveData
import com.example.footballmatchschedule.model.database.LMEDatabase
import com.example.footballmatchschedule.model.database.LeagueDatabase
import com.example.footballmatchschedule.model.database.Note
import com.example.footballmatchschedule.other.jetpack.UserDao

class DatabaseOperator(private var userDao: UserDao) {
    fun readLeagueList(): LiveData<List<LeagueDatabase>> { return userDao.readLeagueList() }

    fun addLeague(league: LeagueDatabase) { userDao.addLeague(league) }

    fun addNote(note: Note) { userDao.addNote(note) }

    fun readNote(name: String): Note { return userDao.readNote(name) }

    fun addLastMatchEvent(lastMatchEvent: LMEDatabase) { userDao.addLastMatchEvent(lastMatchEvent) }

    fun readLastMatchEventList(): LiveData<List<LMEDatabase>> { return userDao.readLastMatchEventList() }

    fun readLastMatchEventList2(id: String): List<LMEDatabase> { return userDao.readLastMatchEventList2(id) }

}