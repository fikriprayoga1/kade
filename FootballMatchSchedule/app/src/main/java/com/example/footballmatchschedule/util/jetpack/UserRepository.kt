package com.example.footballmatchschedule.util.jetpack

import com.example.footballmatchschedule.model.apiresponse.*
import com.example.footballmatchschedule.util.helper.DatabaseHandler
import com.example.footballmatchschedule.util.helper.FMSHelper
import com.example.footballmatchschedule.util.helper.ResponseListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val webservice: Webservice, private val userDao: UserDao) {
    val databaseHandler = DatabaseHandler(userDao)

    fun requestLeagueList(responseListener: ResponseListener) {
        webservice.requestLeague().enqueue(object : Callback<League> {
            override fun onFailure(call: Call<League>, t: Throwable) {
                FMSHelper.errorResponseHandler(
                    responseListener,
                    t,
                    "onFailure, requestLeagueList, UserRepository"
                )

            }

            override fun onResponse(call: Call<League>, response: Response<League>) {
                FMSHelper.responseHandlerOK(response, responseListener)

            }

        })

    }

    fun requestLMEList(id: String, responseListener: ResponseListener) {
        val leagueId = id.toInt()
        webservice.readLastMatch(leagueId).enqueue(object : Callback<Event> {
            override fun onFailure(call: Call<Event>, t: Throwable) {
                FMSHelper.errorResponseHandler(
                    responseListener,
                    t,
                    "onFailure, requestLMEList, UserRepository"
                )

            }

            override fun onResponse(
                call: Call<Event>,
                response: Response<Event>
            ) {
                FMSHelper.responseHandlerOK(response, responseListener)

            }
        })

    }

    fun requestNMEList(id: String, responseListener: ResponseListener) {
        val leagueId = id.toInt()
        webservice.readNextMatch(leagueId).enqueue(object : Callback<Event> {
            override fun onFailure(call: Call<Event>, t: Throwable) {
                FMSHelper.errorResponseHandler(
                    responseListener,
                    t,
                    "onFailure, requestNMEList, UserRepository"
                )

            }

            override fun onResponse(
                call: Call<Event>,
                response: Response<Event>
            ) {
                FMSHelper.responseHandlerOK(response, responseListener)

            }
        })

    }

    fun requestSearchEvent(keyword: String, responseListener: ResponseListener) {
        webservice.readSearchEvent(keyword).enqueue(object : Callback<SearchEvent> {
            override fun onFailure(call: Call<SearchEvent>, t: Throwable) {
                FMSHelper.errorResponseHandler(
                    responseListener,
                    t,
                    "onFailure, requestSearchEventList, UserRepository"
                )

            }

            override fun onResponse(
                call: Call<SearchEvent>,
                response: Response<SearchEvent>
            ) {
                FMSHelper.responseHandlerOK(response, responseListener)

            }
        })

    }

    fun requestTeamList(responseListener: ResponseListener, leagueName: String) {
        webservice.readTeamList(leagueName).enqueue(object : Callback<Team> {
            override fun onFailure(call: Call<Team>, t: Throwable) {
                FMSHelper.errorResponseHandler(
                    responseListener,
                    t,
                    "onFailure, requestTeamList, UserRepository"
                )

            }

            override fun onResponse(
                call: Call<Team>,
                response: Response<Team>
            ) {
                FMSHelper.responseHandlerOK(response, responseListener)

            }
        })

    }

    fun requestSearchTeam(responseListener: ResponseListener, keyword: String) {
        webservice.readSearchTeam(keyword).enqueue(object : Callback<Team> {
            override fun onFailure(call: Call<Team>, t: Throwable) {
                FMSHelper.errorResponseHandler(
                    responseListener,
                    t,
                    "onFailure, requestSearchTeamList, UserRepository"
                )

            }

            override fun onResponse(
                call: Call<Team>,
                response: Response<Team>
            ) {
                FMSHelper.responseHandlerOK(response, responseListener)

            }
        })

    }

    fun requestTeamDetail(responseListener: ResponseListener, id: String) {
        webservice.readTeamDetail(id).enqueue(object : Callback<Team> {
            override fun onFailure(call: Call<Team>, t: Throwable) {
                FMSHelper.errorResponseHandler(
                    responseListener,
                    t,
                    "onFailure, requestTeamDetail, UserRepository"
                )

            }

            override fun onResponse(
                call: Call<Team>,
                response: Response<Team>
            ) {
                FMSHelper.responseHandlerOK(response, responseListener)

            }
        })

    }

    fun requestPlayerList(responseListener: ResponseListener, teamName: String) {
        webservice.readPlayerList(teamName).enqueue(object : Callback<Player> {
            override fun onFailure(call: Call<Player>, t: Throwable) {
                FMSHelper.errorResponseHandler(
                    responseListener,
                    t,
                    "onFailure, requestPlayerList, UserRepository"
                )

            }

            override fun onResponse(
                call: Call<Player>,
                response: Response<Player>
            ) {
                FMSHelper.responseHandlerOK(response, responseListener)

            }
        })

    }

}