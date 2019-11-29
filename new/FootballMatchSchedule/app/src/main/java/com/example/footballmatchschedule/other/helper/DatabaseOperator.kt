package com.example.footballmatchschedule.other.helper

import androidx.lifecycle.LiveData
import com.example.footballmatchschedule.model.database.League
import com.example.footballmatchschedule.other.jetpack.UserDao

class DatabaseOperator(private var userDao: UserDao) {
    fun getLeagueList(): LiveData<List<League>> { return userDao.readLeagueList() }

    fun setLeagueList(league: League) { userDao.setLeagueList(league) }

}