package com.example.footballmatchschedule.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LeagueDatabase (
    @PrimaryKey
    var idLeague: String,
    var strLeague: String,
    var strSport: String,
    var strLeagueAlternate: String
)