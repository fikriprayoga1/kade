package com.example.footballmatchschedule.other.jetpack

import com.example.footballmatchschedule.model.database.League
import com.example.footballmatchschedule.model.retrofitresponse.RequestLeagueList
import com.example.footballmatchschedule.other.callback.Event
import com.example.footballmatchschedule.other.helper.DatabaseOperator
import com.example.footballmatchschedule.view.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class UserRepository(
    private val webservice: Webservice,
    private val userDao: UserDao
) {
    fun getDatabase(): DatabaseOperator {
        return DatabaseOperator(userDao)
    }

    fun requestLeagueList(mainActivity: MainActivity, requestListener: Event) {
        webservice.requestLeagueList().enqueue(object : retrofit2.Callback<com.example.footballmatchschedule.model.apiresponse.League> {
            override fun onFailure(call: Call<com.example.footballmatchschedule.model.apiresponse.League>, t: Throwable) {
                GlobalScope.launch(Dispatchers.Default) {
                    var bug0 = t.message
                    if (bug0 == null) { bug0 = "Message is null" }
                    requestListener.requestLeagueList(mainActivity, RequestLeagueList(false, bug0, null))

                }

            }

            override fun onResponse(call: Call<com.example.footballmatchschedule.model.apiresponse.League>, response: Response<com.example.footballmatchschedule.model.apiresponse.League>) {
                val rb = response.body()

                if (rb != null) {
                    GlobalScope.launch(Dispatchers.Default) {
                        for (i in rb.leagues.indices) {
                            val mObj = rb.leagues[i]

                            val objIdLeague = if(mObj.idLeague != null) {
                                mObj.idLeague!!.toInt()

                            } else { 0 }
                            val objStrLeague = if (mObj.strLeague != null) {
                                mObj.strLeague!!

                            } else { "null" }

                            val objStrSport = if(mObj.strSport != null) {
                                mObj.strSport!!

                            } else { "null" }

                            val objStrLeagueAlternate = if (mObj.strLeagueAlternate != null) {
                                mObj.strLeagueAlternate!!

                            } else { "null" }

                            DatabaseOperator(userDao).setLeagueList(
                                League(
                                    objIdLeague,
                                    objStrLeague,
                                    objStrSport,
                                    objStrLeagueAlternate
                                )
                            )

                        }

                    }

                    requestListener.requestLeagueList(mainActivity, RequestLeagueList(
                        true,
                        "Request response is OK",
                        response.body()
                    ))

                } else {
                    requestListener.requestLeagueList(mainActivity, RequestLeagueList(
                        false,
                        "Response body is null",
                        response.body()
                    ))

                }


            }

        })

    }

}