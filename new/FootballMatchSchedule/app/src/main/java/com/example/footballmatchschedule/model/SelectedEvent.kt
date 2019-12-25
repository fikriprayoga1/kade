package com.example.footballmatchschedule.model

data class SelectedEvent(
    var dateEvent: String?,
    var idHomeTeam: String?,
    var idAwayTeam: String?,
    var strHomeTeam: String?,
    var strAwayTeam: String?,
    var intHomeScore: String?,
    var intAwayScore: String?,
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
    var strAwayLineupSubstitutes: String?
)