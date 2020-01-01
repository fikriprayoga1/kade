package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.EventDetail
import com.example.footballmatchschedule.model.apiresponse.TeamDetail
import com.example.footballmatchschedule.model.database.EventDatabase
import com.example.footballmatchschedule.model.database.TeamDatabase
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.other.recyclerviewadapter.TeamRecyclerViewAdapter
import com.example.footballmatchschedule.view.MainActivity

class SearchTeamViewModel : ViewModel() {
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

    fun initTeamList(teamList: List<TeamDetail>?) {
        teamObjects.clear()

        if (teamList != null) {
            for (i in teamList.indices) {
                if (teamList[i].idTeam != null) {
                    teamObject = TeamRecyclerViewAdapter.TeamObject(getTeamObject(teamList[i]))
                    teamObjects.add(teamObject)

                }

            }

        }

    }

    fun getTeamObjects(): MutableList<TeamRecyclerViewAdapter.TeamObject> {
        return teamObjects
    }

    fun getMainActivity(): MainActivity {
        return mainActivity
    }

    private fun getTeamObject(teamDetail: TeamDetail): TeamDatabase {
        return TeamDatabase(
            teamDetail.idTeam!!,
            null,
            teamDetail.strTeamBadge,
            teamDetail.strTeam
        )

    }

}
