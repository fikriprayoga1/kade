package com.example.footballmatchschedule.other.jetpack

import android.util.Log
import com.example.footballmatchschedule.model.RetrofitResponse
import com.example.footballmatchschedule.model.apiresponse.LME
import com.example.footballmatchschedule.model.apiresponse.League
import com.example.footballmatchschedule.model.apiresponse.NME
import com.example.footballmatchschedule.other.ResponseListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val webservice: Webservice) {
    val tag = "football_match_schedule"

    fun requestLeagueList(responseListener: ResponseListener) {
        var isSuccess = false
        var message: String

        webservice.requestLeagueList().enqueue(object : Callback<League> {
            override fun onFailure(call: Call<League>, t: Throwable) {
                GlobalScope.launch(Dispatchers.Default) {
                    Log.d(tag, "UserRepository/25 : ${t.message}")
                    Log.d(tag, "UserRepository/26 : ${t.cause}")
                    responseListener.retrofitResponse(
                        RetrofitResponse(
                            isSuccess,
                            "onFailure, requestLeagueList, UserRepository",
                            null
                        )
                    )

                }

            }

            override fun onResponse(call: Call<League>, response: Response<League>) {
                GlobalScope.launch(Dispatchers.Default) {
                    val rb = response.body()

                    if (rb != null) {
                        val leagueList = rb.leagues
                        if (leagueList != null) {
                            isSuccess = true
                            message = "Request response is OK"

                        } else {
                            message = "League list is null"
                        }

                    } else {
                        message = "Response body is null"
                    }

                    responseListener.retrofitResponse(
                        RetrofitResponse(
                            isSuccess,
                            message,
                            rb
                        )
                    )

                }

            }

        })

    }

    fun requestLMEList(id: String, responseListener: ResponseListener) {
        var isSuccess = false
        var message = ""

        val leagueId = id.toInt()
        webservice.readLastMatch(leagueId).enqueue(object : Callback<LME> {
            override fun onFailure(call: Call<LME>, t: Throwable) {
                GlobalScope.launch(Dispatchers.Default) {
                    Log.d(tag, "UserRepository/81 : ${t.message}")
                    Log.d(tag, "UserRepository/82 : ${t.cause}")
                    responseListener.retrofitResponse(
                        RetrofitResponse(
                            isSuccess,
                            "onFailure, requestLMEList, UserRepository",
                            null
                        )
                    )

                }

            }

            override fun onResponse(
                call: Call<LME>,
                response: Response<LME>
            ) {
                GlobalScope.launch(Dispatchers.Default) {
                    val rb = response.body()
                    if (rb != null) {
                        val lastMatchEventList = rb.events
                        if (lastMatchEventList != null) {
                            isSuccess = true
                            message = "Request response is OK"

                        } else {
                            message = "Last Match Event List is null"
                        }

                    } else {
                        message = "Response body is null"
                    }

                    responseListener.retrofitResponse(
                        RetrofitResponse(
                            isSuccess,
                            message,
                            rb
                        )
                    )

                }

            }
        })

    }

    fun requestNMEList(id: String, responseListener: ResponseListener) {
        var isSuccess = false
        var message = ""

        val leagueId = id.toInt()
        webservice.readNextMatch(leagueId).enqueue(object : Callback<NME> {
            override fun onFailure(call: Call<NME>, t: Throwable) {
                GlobalScope.launch(Dispatchers.Default) {
                    responseListener.retrofitResponse(
                        RetrofitResponse(
                            isSuccess,
                            "onFailure, requestNMEList, UserRepository",
                            null
                        )
                    )

                }

            }

            override fun onResponse(
                call: Call<NME>,
                response: Response<NME>
            ) {
                GlobalScope.launch(Dispatchers.Default) {
                    val rb = response.body()
                    if (rb != null) {
                        val nextMatchEventList = rb.events
                        if (nextMatchEventList != null) {
                            isSuccess = true
                            message = "Request response is OK"

                        } else {
                            message = "Next Match Event List is null"
                        }

                    } else {
                        message = "Response body is null"
                    }

                    responseListener.retrofitResponse(
                        RetrofitResponse(
                            isSuccess,
                            message,
                            rb
                        )
                    )

                }

            }
        })

    }

}