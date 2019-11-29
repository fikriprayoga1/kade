package com.example.footballmatchschedule.other.callback

import com.example.footballmatchschedule.model.retrofitresponse.RequestLeagueList
import com.example.footballmatchschedule.view.MainActivity

interface Event {
    fun requestLeagueList(mainActivity: MainActivity, requestLeagueList: RequestLeagueList)

}