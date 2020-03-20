package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.database.TeamDatabase
import com.example.footballmatchschedule.util.jetpack.UserRepository
import com.example.footballmatchschedule.util.recyclerviewadapter.TeamRecyclerViewAdapter
import com.example.footballmatchschedule.MainActivity

class FavoriteTeamViewModel : ViewModel() {
    // 1
    private var userRepository: UserRepository? = null

    // 2
    private var favoriteTeam: LiveData<List<TeamDatabase>>? = null

    // 3
    private val teamObjects: MutableList<TeamRecyclerViewAdapter.TeamObject> = ArrayList()

    // 4
    private lateinit var teamObject: TeamRecyclerViewAdapter.TeamObject

    fun init(userRepository: UserRepository) {
        this.userRepository = userRepository

        if (favoriteTeam == null) {
            favoriteTeam = userRepository.databaseHandler.readFavoriteTeam()

        }

    }

    fun getFavoriteTeam(): LiveData<List<TeamDatabase>>? {
        return favoriteTeam
    }

    fun initTeamList(teamList: List<TeamDatabase>?) {
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

}
