package id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class MainHandler {
    fun init(): ApiList? {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.thesportsdb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(ApiList::class.java)

    }
}

interface ApiList {
    @GET("api/v1/json/1/eventspastleague.php")
    fun readLastMatch(
            @Query("id") id: Int
    ): Call<Any>

    @GET("api/v1/json/1/eventsnextleague.php")
    fun readNextMatch(
            @Query("id") id: Int
    ): Call<Any>

    @GET("api/v1/json/1/lookupteam.php")
    fun readDetailTeam(
            @Query("id") id: Int
    ): Call<Any>

    @GET("api/v1/json/1/all_leagues.php")
    fun readLeagueList(): Call<Any>

    @GET("api/v1/json/1/searchevents.php")
    fun readSearchEvent(
            @Query("e") keyword: String
    ): Call<Any>

    @GET("api/v1/json/1/search_all_teams.php")
    fun readTeamList(
            @Query("l") id: String
    ): Call<Any>

    @GET("api/v1/json/1/searchteams.php")
    fun readSearchTeam(
            @Query("t") keyword: String
    ): Call<Any>

    @GET("api/v1/json/1/lookup_all_players.php")
    fun readPlayer(
            @Query("id") ID: Int
    ): Call<Any>

    @GET("api/v1/json/1/lookupplayer.php")
    fun readPlayerDetail(
            @Query("id") ID: Int
    ): Call<Any>

}