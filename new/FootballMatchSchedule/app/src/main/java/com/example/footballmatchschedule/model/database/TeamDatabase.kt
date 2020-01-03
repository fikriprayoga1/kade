package com.example.footballmatchschedule.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TeamDatabase(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var idTeam: String?,
    var isFavorite: Boolean?,
    var strTeamBadge: String?,
    var strTeam: String?,
    var intFormedYear: String?,
    var strStadium: String?,
    var strDescriptionEN: String?
)