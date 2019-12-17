package com.example.footballmatchschedule.model.apiresponse

data class SearchTeam(
    var teams: List<SearchTeamDetail> = listOf()
)