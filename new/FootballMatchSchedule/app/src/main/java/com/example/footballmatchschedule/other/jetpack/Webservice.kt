package com.example.footballmatchschedule.other.jetpack

import com.example.footballmatchschedule.model.apiresponse.LME
import com.example.footballmatchschedule.model.apiresponse.League
import com.example.footballmatchschedule.model.apiresponse.NME
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Webservice {
    //    @Headers("Content-Type: application/json")
    @GET("api/v1/json/1/all_leagues.php")
    fun requestLeagueList(): Call<League>

    @GET("api/v1/json/1/eventspastleague.php")
    fun readLastMatch(@Query("id") id: Int): Call<LME>

    @GET("api/v1/json/1/eventsnextleague.php")
    fun readNextMatch(@Query("id") id: Int): Call<NME>

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