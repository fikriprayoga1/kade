package com.example.footballmatchschedule.other.callback

import com.example.footballmatchschedule.model.retrofitresponse.RequestLeague
import com.example.footballmatchschedule.view.MainActivity

interface RequestLeagueCallback {
    fun requestLeagueList(mainActivity: MainActivity, requestLeague: RequestLeague)

}