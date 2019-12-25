package com.example.footballmatchschedule.viewmodel

import android.R
import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.LeagueDetail
import com.example.footballmatchschedule.model.apiresponse.TeamDetail
import com.example.footballmatchschedule.other.ResponseListener
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.other.recyclerviewadapter.TeamRecyclerViewAdapter
import com.example.footballmatchschedule.view.MainActivity

class TeamViewModel : ViewModel() {
    // 1
    private lateinit var userRepository: UserRepository
    // 2
    private lateinit var mainActivity: MainActivity
    // 3
    private val teamObjects: MutableList<TeamRecyclerViewAdapter.TeamObject> = ArrayList()
    // 4
    private lateinit var teamObject: TeamRecyclerViewAdapter.TeamObject

    fun init(userRepository: UserRepository, mainActivity: MainActivity) {
        this.userRepository = userRepository
        this.mainActivity = mainActivity

    }

    fun requestLeagueList(responseListener: ResponseListener) {
        userRepository.requestLeagueList(responseListener)

    }

    fun requestTeamList(responseListener: ResponseListener, leagueName: String) {
        userRepository.requestTeamList(responseListener, leagueName)

    }

    fun initTeamList(teamList: List<TeamDetail>?) {
        teamObjects.clear()

        if (teamList != null) {
            for (i in teamList.indices) {
                teamObject = TeamRecyclerViewAdapter.TeamObject(teamList[i])
                teamObjects.add(teamObject)

            }

        }

    }

    fun getTeamObjects(): MutableList<TeamRecyclerViewAdapter.TeamObject> {
        return teamObjects
    }

    fun getMainActivity(): MainActivity {
        return mainActivity
    }

    fun addSpinner(it: List<LeagueDetail>?, context: Context): ArrayAdapter<String> {
        val spinnerNameList = ArrayList<String>()

        if (it != null) {
            for (i in it.indices) {
                val a1 = it[i].strLeague
                if (a1 != null) {
                    spinnerNameList.add(a1)

                }

            }

        }

        val aa = ArrayAdapter(context, R.layout.simple_spinner_item, spinnerNameList)
        aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        return aa

    }

    fun requestSearchTeam(responseListener: ResponseListener, keyword: String?) {
        val keyword2 = keyword ?: ""
        userRepository.requestSearchTeam(responseListener, keyword2)

    }

}
