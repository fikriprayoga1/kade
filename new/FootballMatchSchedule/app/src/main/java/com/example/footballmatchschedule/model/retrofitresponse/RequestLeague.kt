package com.example.footballmatchschedule.model.retrofitresponse

import com.example.footballmatchschedule.model.apiresponse.League

data class RequestLeague(
    var isSuccess: Boolean,
    var message: String,
    var league: League?
)