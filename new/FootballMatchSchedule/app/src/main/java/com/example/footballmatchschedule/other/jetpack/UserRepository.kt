package com.example.footballmatchschedule.other.jetpack

import com.example.footballmatchschedule.model.apiresponse.LME
import com.example.footballmatchschedule.model.apiresponse.League
import com.example.footballmatchschedule.model.database.LeagueDatabase
import com.example.footballmatchschedule.model.retrofitresponse.RequestLME
import com.example.footballmatchschedule.model.retrofitresponse.RequestLeague
import com.example.footballmatchschedule.other.callback.RequestLMECallback
import com.example.footballmatchschedule.other.callback.RequestLeagueCallback
import com.example.footballmatchschedule.other.helper.DatabaseOperator
import com.example.footballmatchschedule.view.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(
    private val webservice: Webservice,
    private val userDao: UserDao
) {
    fun getDatabase(): DatabaseOperator {
        return DatabaseOperator(userDao)
    }

    fun requestLeagueList(mainActivity: MainActivity, requestListener: RequestLeagueCallback) {
        webservice.requestLeagueList().enqueue(object : Callback<League> {
            override fun onFailure(call: Call<League>, t: Throwable) {
                GlobalScope.launch(Dispatchers.Default) {
                    var bug0 = t.message
                    if (bug0 == null) { bug0 = "Message is null" }
                    requestListener.requestLeagueList(mainActivity, RequestLeague(false, bug0, null))

                }

            }

            override fun onResponse(call: Call<League>, response: Response<League>) {
                GlobalScope.launch(Dispatchers.Default) {
                    val rb = response.body()

                    if (rb != null) {
                        val leagueList = rb.leagues
                        if (leagueList != null) {
                            for (i in leagueList.indices) {
                                val mObj = leagueList[i]

                                if (mObj != null) {
                                    val objIdLeague = if(mObj.idLeague != null) {
                                        mObj.idLeague!!

                                    } else { "null" }

                                    val objStrLeague = if (mObj.strLeague != null) {
                                        mObj.strLeague!!

                                    } else { "null" }

                                    val objStrSport = if(mObj.strSport != null) {
                                        mObj.strSport!!

                                    } else { "null" }

                                    val objStrLeagueAlternate = if (mObj.strLeagueAlternate != null) {
                                        mObj.strLeagueAlternate!!

                                    } else { "null" }

                                    DatabaseOperator(userDao).addLeague(
                                        LeagueDatabase(
                                            objIdLeague,
                                            objStrLeague,
                                            objStrSport,
                                            objStrLeagueAlternate
                                        )
                                    )

                                }

                            }

                            requestListener.requestLeagueList(mainActivity, RequestLeague(
                                true,
                                "Request response is OK",
                                response.body()
                            ))

                        } else {
                            requestListener.requestLeagueList(mainActivity, RequestLeague(
                                false,
                                "League list is null",
                                response.body()
                            ))

                        }

                    } else {
                        requestListener.requestLeagueList(mainActivity, RequestLeague(
                            false,
                            "Response body is null",
                            response.body()
                        ))

                    }

                }

            }

        })

    }

    fun requestLastMatchList(id: String, mainActivity: MainActivity, requestListener: RequestLMECallback) {
        val leagueId = id.toInt()
        webservice.readLastMatch(leagueId).enqueue(object : Callback<LME> {
            override fun onFailure(call: Call<LME>, t: Throwable) {
                GlobalScope.launch(Dispatchers.Default) {
                    var bug0 = t.message
                    if (bug0 == null) { bug0 = "Message is null" }
                    requestListener.requestLMEList(mainActivity,
                        RequestLME(false, bug0, null)
                    )

                }

            }

            override fun onResponse(
                call: Call<LME>,
                response: Response<LME>
            ) {
                val rb = response.body()
                if (rb != null) {
                    val lastMatchEventList = rb.events
                    if (lastMatchEventList != null) {


                    } else {


                    }

                } else {
                    requestListener

                    requestListener.requestLMEList(mainActivity, RequestLME(
                        false,
                        "Response body is null",
                        response.body()
                    ))

                }

            }
        })

    }

}