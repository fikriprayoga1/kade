package com.example.footballmatchschedule.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TeamDatabase(
    @PrimaryKey
    var idTeam: String,
    var isFavorite: Boolean?,
    var strTeamBadge: String?,
    var strTeam: String?
)