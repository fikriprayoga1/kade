package id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller.searchteam

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.R
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.MainHandler
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller.MainActivity
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller.TeamDetailFragment
import kotlinx.android.synthetic.main.activity_search_team.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchTeamPresenter(private val searchTeamView: SearchTeamView) {
    private var searchTeamModel: SearchTeamModel? = null

    private fun luasPersegi(panjang: Int, lebar: Int): Int {
        return panjang * lebar
    }

    fun getPlayer(TeamID: Int, itemData: Bundle) {
        val api = MainHandler().init()
        val call = api!!.readPlayer(TeamID)

        call.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                searchTeamModel = SearchTeamModel(t)
                searchTeamView.onFailure(searchTeamModel!!)

            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Log.d("DICODING", "SearchTeamPresenter/35 : " + response.body())
                searchTeamModel = SearchTeamModel(response.body().toString(), itemData)
                searchTeamView.onResponse(searchTeamModel!!)

            }

        })

    }

}