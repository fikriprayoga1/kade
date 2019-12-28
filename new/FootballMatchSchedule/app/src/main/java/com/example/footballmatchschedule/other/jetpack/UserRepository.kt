package com.example.footballmatchschedule.other.jetpack

import androidx.lifecycle.LiveData
import com.example.footballmatchschedule.model.RetrofitResponse
import com.example.footballmatchschedule.model.apiresponse.*
import com.example.footballmatchschedule.other.ResponseListener
import com.example.footballmatchschedule.other.helper.TagHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val webservice: Webservice, private val userDao: UserDao) {
    fun createEvent(eventDetail: EventDetail) {
        userDao.createEvent(eventDetail)

    }

    fun readEvent(idEvent: String) : List<EventDetail> {
        return userDao.readEvent(idEvent)

    }

    fun readFavoriteEvent(): LiveData<List<EventDetail>> {
        return userDao.readFavoriteEvent()

    }

    fun readFavoriteTeam(): LiveData<List<TeamDetail>> {
        return userDao.readFavoriteTeam()

    }

    fun deleteEvent(idEvent: String) {
        userDao.deleteEvent(idEvent)

    }

    fun requestLeagueList(responseListener: ResponseListener) {
        var isSuccess = false
        var message: String

        webservice.requestLeague().enqueue(object : Callback<League> {
            override fun onFailure(call: Call<League>, t: Throwable) {
                TagHelper().writeTag(
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    t.message
                )
                TagHelper().writeTag(
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    t.cause.toString()
                )
                responseListener.retrofitResponse(
                    RetrofitResponse(
                        isSuccess,
                        "onFailure, requestLeagueList, UserRepository",
                        null
                    )
                )

            }

            override fun onResponse(call: Call<League>, response: Response<League>) {
                val rb = response.body()

                if (rb != null) {
                    isSuccess = true
                    message = "Request response is OK"

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

        })

    }

    fun requestLMEList(id: String, responseListener: ResponseListener) {
        var isSuccess = false
        var message: String

        val leagueId = id.toInt()
        webservice.readLastMatch(leagueId).enqueue(object : Callback<Event> {
            override fun onFailure(call: Call<Event>, t: Throwable) {
                TagHelper().writeTag(
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    t.message
                )
                TagHelper().writeTag(
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    t.cause.toString()
                )
                responseListener.retrofitResponse(
                    RetrofitResponse(
                        isSuccess,
                        "onFailure, requestLMEList, UserRepository",
                        null
                    )
                )

            }

            override fun onResponse(
                call: Call<Event>,
                response: Response<Event>
            ) {
                val rb = response.body()
                if (rb != null) {
                    isSuccess = true
                    message = "Request response is OK"

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
        })

    }

    fun requestNMEList(id: String, responseListener: ResponseListener) {
        var isSuccess = false
        var message: String

        val leagueId = id.toInt()
        webservice.readNextMatch(leagueId).enqueue(object : Callback<Event> {
            override fun onFailure(call: Call<Event>, t: Throwable) {
                TagHelper().writeTag(
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    t.message
                )
                TagHelper().writeTag(
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    t.cause.toString()
                )
                responseListener.retrofitResponse(
                    RetrofitResponse(
                        isSuccess,
                        "onFailure, requestNMEList, UserRepository",
                        null
                    )
                )

            }

            override fun onResponse(
                call: Call<Event>,
                response: Response<Event>
            ) {
                val rb = response.body()
                if (rb != null) {
                    isSuccess = true
                    message = "Request response is OK"

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
        })

    }

    fun requestSearchEvent(keyword: String, responseListener: ResponseListener) {
        var isSuccess = false
        var message: String

        webservice.readSearchEvent(keyword).enqueue(object : Callback<SearchEvent> {
            override fun onFailure(call: Call<SearchEvent>, t: Throwable) {
                TagHelper().writeTag(
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    t.message
                )
                TagHelper().writeTag(
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    t.cause.toString()
                )
                responseListener.retrofitResponse(
                    RetrofitResponse(
                        isSuccess,
                        "onFailure, requestSearchEventList, UserRepository",
                        null
                    )
                )

            }

            override fun onResponse(
                call: Call<SearchEvent>,
                response: Response<SearchEvent>
            ) {
                val rb = response.body()
                if (rb != null) {
                    if (rb.event != null) {
                        isSuccess = true
                        message = "Request response is OK"
                    } else {
                        message = "No search found"

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
        })

    }

    fun requestTeamList(responseListener: ResponseListener, leagueName: String) {
        var isSuccess = false
        var message: String

        webservice.readTeamList(leagueName).enqueue(object : Callback<Team> {
            override fun onFailure(call: Call<Team>, t: Throwable) {
                TagHelper().writeTag(
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    t.message
                )
                TagHelper().writeTag(
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    t.cause.toString()
                )
                responseListener.retrofitResponse(
                    RetrofitResponse(
                        isSuccess,
                        "onFailure, requestTeamList, UserRepository",
                        null
                    )
                )

            }

            override fun onResponse(
                call: Call<Team>,
                response: Response<Team>
            ) {
                val rb = response.body()
                if (rb != null) {
                    isSuccess = true
                    message = "Request response is OK"

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
        })

    }

    fun requestSearchTeam(responseListener: ResponseListener, keyword: String) {
        var isSuccess = false
        var message: String

        webservice.readSearchTeam(keyword).enqueue(object : Callback<Team> {
            override fun onFailure(call: Call<Team>, t: Throwable) {
                TagHelper().writeTag(
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    t.message
                )
                TagHelper().writeTag(
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    t.cause.toString()
                )
                responseListener.retrofitResponse(
                    RetrofitResponse(
                        isSuccess,
                        "onFailure, requestSearchTeamList, UserRepository",
                        null
                    )
                )

            }

            override fun onResponse(
                call: Call<Team>,
                response: Response<Team>
            ) {
                val rb = response.body()
                if (rb != null) {
                    if (rb.teams != null) {
                        isSuccess = true
                        message = "Request response is OK"

                    } else {
                        message = "No search found"

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
        })

    }

    fun requestTeamDetail(responseListener: ResponseListener, id: String) {
        var isSuccess = false
        var message: String

        webservice.readTeamDetail(id).enqueue(object : Callback<TeamDetail> {
            override fun onFailure(call: Call<TeamDetail>, t: Throwable) {
                TagHelper().writeTag(
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    t.message
                )
                TagHelper().writeTag(
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    t.cause.toString()
                )
                responseListener.retrofitResponse(
                    RetrofitResponse(
                        isSuccess,
                        "onFailure, requestTeamDetail, UserRepository",
                        null
                    )
                )

            }

            override fun onResponse(
                call: Call<TeamDetail>,
                response: Response<TeamDetail>
            ) {
                val rb = response.body()
                if (rb != null) {
                    isSuccess = true
                    message = "Request response is OK"

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
        })

    }

}