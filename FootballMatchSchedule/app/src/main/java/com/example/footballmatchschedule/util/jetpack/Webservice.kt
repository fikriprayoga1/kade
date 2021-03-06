package com.example.footballmatchschedule.util.jetpack

import com.example.footballmatchschedule.model.apiresponse.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Webservice {
    @GET("api/v1/json/1/all_leagues.php")
    fun requestLeague(): Call<League>

    @GET("api/v1/json/1/eventspastleague.php")
    fun readLastMatch(@Query("id") id: Int): Call<Event>

    @GET("api/v1/json/1/eventsnextleague.php")
    fun readNextMatch(@Query("id") id: Int): Call<Event>

    @GET("api/v1/json/1/searchevents.php")
    fun readSearchEvent(@Query("e") keyword: String): Call<SearchEvent>

    @GET("api/v1/json/1/search_all_teams.php")
    fun readTeamList(@Query("l") leagueName: String): Call<Team>

    @GET("api/v1/json/1/searchteams.php")
    fun readSearchTeam(@Query("t") keyword: String): Call<Team>

    @GET("api/v1/json/1/lookupteam.php")
    fun readTeamDetail(@Query("id") id: String): Call<Team>

    @GET("api/v1/json/1/searchplayers.php")
    fun readPlayerList(@Query("t") teamName: String): Call<Player>


    companion object Factory {
        fun create(): Webservice {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.thesportsdb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(Webservice::class.java)
        }
    }

}