package com.example.footballmatchschedule.viewmodel

import android.R
import android.content.Context
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.LeagueDetail
import com.example.footballmatchschedule.model.apiresponse.TeamDetail
import com.example.footballmatchschedule.model.database.TeamDatabase
import com.example.footballmatchschedule.util.helper.ResponseListener
import com.example.footballmatchschedule.util.jetpack.UserRepository
import com.example.footballmatchschedule.util.recyclerviewadapter.TeamRecyclerViewAdapter
import com.example.footballmatchschedule.MainActivity

class TeamViewModel : ViewModel() {
    // 1
    private lateinit var userRepository: UserRepository

    // 2
    private val teamObjects: MutableList<TeamRecyclerViewAdapter.TeamObject> = ArrayList()

    // 3
    private lateinit var teamObject: TeamRecyclerViewAdapter.TeamObject

    fun init(userRepository: UserRepository) {
        this.userRepository = userRepository

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
                teamObject = TeamRecyclerViewAdapter.TeamObject(getTeamObject(teamList[i]))
                teamObjects.add(teamObject)

            }

        }

    }

    fun getTeamObjects(): MutableList<TeamRecyclerViewAdapter.TeamObject> {
        return teamObjects
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

    private fun getTeamObject(teamDetail: TeamDetail): TeamDatabase {
        return TeamDatabase(
            0,
            teamDetail.idTeam!!,
            null,
            teamDetail.strTeamBadge,
            teamDetail.strTeam,
            teamDetail.intFormedYear,
            teamDetail.strStadium,
            teamDetail.strDescriptionEN
        )

    }

}
