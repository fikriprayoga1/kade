package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.TeamDetail
import com.example.footballmatchschedule.model.database.TeamDatabase
import com.example.footballmatchschedule.util.jetpack.UserRepository
import com.example.footballmatchschedule.util.recyclerviewadapter.TeamRecyclerViewAdapter
import com.example.footballmatchschedule.MainActivity

class SearchTeamViewModel : ViewModel() {
    // 1
    private lateinit var userRepository: UserRepository

    // 2
    private val teamObjects: MutableList<TeamRecyclerViewAdapter.TeamObject> = ArrayList()

    // 3
    private lateinit var teamObject: TeamRecyclerViewAdapter.TeamObject

    fun init(userRepository: UserRepository) {
        this.userRepository = userRepository

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
