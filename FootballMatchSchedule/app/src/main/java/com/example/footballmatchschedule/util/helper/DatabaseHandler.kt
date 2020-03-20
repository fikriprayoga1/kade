package com.example.footballmatchschedule.util.helper

import androidx.lifecycle.LiveData
import com.example.footballmatchschedule.model.database.EventDatabase
import com.example.footballmatchschedule.model.database.TeamDatabase
import com.example.footballmatchschedule.util.jetpack.UserDao

class DatabaseHandler(private val userDao: UserDao) {
    fun createEvent(eventDatabase: EventDatabase) {
        userDao.createEvent(eventDatabase)

    }

    fun createTeam(teamDatabase: TeamDatabase) {
        userDao.createTeam(teamDatabase)

    }

    fun readEvent(idEvent: String): List<EventDatabase> {
        return userDao.readEvent(idEvent)

    }

    fun readTeam(idTeam: String): List<TeamDatabase> {
        return userDao.readTeam(idTeam)

    }

    fun readFavoriteEvent(): LiveData<List<EventDatabase>> {
        return userDao.readFavoriteEvent()

    }

    fun readAlarmEvent(): LiveData<List<EventDatabase>> {
        return userDao.readAlarmEvent()

    }

    fun readFavoriteTeam(): LiveData<List<TeamDatabase>> {
        return userDao.readFavoriteTeam()

    }

    fun updateEvent(eventDatabase: EventDatabase) {
        userDao.createEvent(eventDatabase)

    }

    fun updateTeam(teamDatabase: TeamDatabase) {
        userDao.updateTeam(teamDatabase)

    }

    fun deleteEvent(eventDatabase: EventDatabase) {
        userDao.deleteEvent(eventDatabase)

    }

    fun deleteTeam(teamDatabase: TeamDatabase) {
        userDao.deleteTeam(teamDatabase)

    }
}