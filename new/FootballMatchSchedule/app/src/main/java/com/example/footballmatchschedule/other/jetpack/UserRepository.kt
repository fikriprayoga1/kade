package com.example.footballmatchschedule.other.jetpack

import android.util.Log
import com.example.footballmatchschedule.model.RetrofitResponse
import com.example.footballmatchschedule.model.apiresponse.*
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

        webservice.requestLeague().enqueue(object : Callback<League> {
            override fun onFailure(call: Call<League>, t: Throwable) {
                GlobalScope.launch(Dispatchers.Default) {
                    Log.d(tag, "UserRepository/24 : ${t.message}")
                    Log.d(tag, "UserRepository/25 : ${t.cause}")
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
                        isSuccess = true
                        message = "Request response is OK"

                    } else { message = "Response body is null" }

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
        var message: String

        val leagueId = id.toInt()
        webservice.readLastMatch(leagueId).enqueue(object : Callback<LME> {
            override fun onFailure(call: Call<LME>, t: Throwable) {
                GlobalScope.launch(Dispatchers.Default) {
                    Log.d(tag, "UserRepository/72 : ${t.message}")
                    Log.d(tag, "UserRepository/73 : ${t.cause}")
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
                        isSuccess = true
                        message = "Request response is OK"

                    } else { message = "Response body is null" }

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
        var message: String

        val leagueId = id.toInt()
        webservice.readNextMatch(leagueId).enqueue(object : Callback<NME> {
            override fun onFailure(call: Call<NME>, t: Throwable) {
                GlobalScope.launch(Dispatchers.Default) {
                    Log.d(tag, "UserRepository/121 : ${t.message}")
                    Log.d(tag, "UserRepository/122 : ${t.cause}")
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
                        isSuccess = true
                        message = "Request response is OK"

                    } else { message = "Response body is null" }

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

    fun requestSearchEvent(keyword: String, responseListener: ResponseListener) {
        var isSuccess = false
        var message: String

        webservice.readSearchEvent(keyword).enqueue(object : Callback<SearchEvent> {
            override fun onFailure(call: Call<SearchEvent>, t: Throwable) {
                GlobalScope.launch(Dispatchers.Default) {
                    Log.d(tag, "UserRepository/169 : ${t.message}")
                    Log.d(tag, "UserRepository/170 : ${t.cause}")
                    responseListener.retrofitResponse(
                        RetrofitResponse(
                            isSuccess,
                            "onFailure, requestSearchEventList, UserRepository",
                            null
                        )
                    )

                }

            }

            override fun onResponse(
                call: Call<SearchEvent>,
                response: Response<SearchEvent>
            ) {
                GlobalScope.launch(Dispatchers.Default) {
                    val rb = response.body()
                    if (rb != null) {
                        isSuccess = true
                        message = "Request response is OK"

                    } else { message = "Response body is null" }

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

    fun requestSearchTeam(keyword: String, responseListener: ResponseListener) {
        var isSuccess = false
        var message = ""

        webservice.readSearchTeam(keyword).enqueue(object : Callback<SearchTeam> {
            override fun onFailure(call: Call<SearchTeam>, t: Throwable) {
                GlobalScope.launch(Dispatchers.Default) {
                    Log.d(tag, "UserRepository/217 : ${t.message}")
                    Log.d(tag, "UserRepository/218 : ${t.cause}")
                    responseListener.retrofitResponse(
                        RetrofitResponse(
                            isSuccess,
                            "onFailure, requestSearchTeamList, UserRepository",
                            null
                        )
                    )

                }

            }

            override fun onResponse(
                call: Call<SearchTeam>,
                response: Response<SearchTeam>
            ) {
                GlobalScope.launch(Dispatchers.Default) {
                    val rb = response.body()
                    if (rb != null) {
                        isSuccess = true
                        message = "Request response is OK"

                    } else { message = "Response body is null" }

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