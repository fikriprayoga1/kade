package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.database.TeamDatabase
import com.example.footballmatchschedule.util.jetpack.UserRepository
import com.example.footballmatchschedule.util.recyclerviewadapter.TeamRecyclerViewAdapter
import com.example.footballmatchschedule.view.MainActivity

class FavoriteTeamViewModel : ViewModel() {
    // 1
    private var userRepository: UserRepository? = null
    // 2
    private lateinit var mainActivity: MainActivity
    // 3
    private var favoriteTeam: LiveData<List<TeamDatabase>>? = null
    // 4
    private val teamObjects: MutableList<TeamRecyclerViewAdapter.TeamObject> = ArrayList()
    // 5
    private lateinit var teamObject: TeamRecyclerViewAdapter.TeamObject

    fun init(userRepository: UserRepository, mainActivity: MainActivity) {
        this.userRepository = userRepository
        this.mainActivity = mainActivity

        if (favoriteTeam == null) {
            favoriteTeam = userRepository.readFavoriteTeam()

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

    fun getMainActivity(): MainActivity {
        return mainActivity
    }

    fun getTeamObjects(): MutableList<TeamRecyclerViewAdapter.TeamObject> {
        return teamObjects
    }

}
