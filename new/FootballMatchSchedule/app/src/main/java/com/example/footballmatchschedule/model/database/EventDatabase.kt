package com.example.footballmatchschedule.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EventDatabase(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var dateEvent: String?,
    var idEvent: String?,
    var strHomeTeam: String?,
    var strAwayTeam: String?,
    var intHomeScore: String?,
    var intAwayScore: String?,
    var idHomeTeam: String?,
    var idAwayTeam: String?,
    var intHomeShots: String?,
    var intAwayShots: String?,
    var strHomeGoalDetails: String?,
    var strAwayGoalDetails: String?,
    var strHomeLineupGoalkeeper: String?,
    var strAwayLineupGoalkeeper: String?,
    var strHomeLineupDefense: String?,
    var strAwayLineupDefense: String?,
    var strHomeLineupMidfield: String?,
    var strAwayLineupMidfield: String?,
    var strHomeLineupForward: String?,
    var strAwayLineupForward: String?,
    var strHomeLineupSubstitutes: String?,
    var strAwayLineupSubstitutes: String?,
    var strLeague: String?,
    var strEvent: String?,
    var isAlarm: String?,
    var isFavorite: Boolean?
)