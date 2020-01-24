package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.PlayerDetail
import com.example.footballmatchschedule.model.database.TeamDatabase
import com.example.footballmatchschedule.other.helper.ResponseListener
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.other.recyclerviewadapter.PlayerRecyclerViewAdapter
import com.example.footballmatchschedule.view.MainActivity

class TeamDetailViewModel : ViewModel() {
    // 1
    private lateinit var userRepository: UserRepository
    // 2
    private lateinit var mainActivity: MainActivity
    // 3
    private val playerObjects: MutableList<PlayerRecyclerViewAdapter.PlayerObject> = ArrayList()
    // 4
    private lateinit var playerObject: PlayerRecyclerViewAdapter.PlayerObject

    fun init(userRepository: UserRepository, mainActivity: MainActivity) {
        this.userRepository = userRepository
        this.mainActivity = mainActivity

    }

    fun getMainActivity(): MainActivity {
        return mainActivity
    }

    fun hasIdEvent(teamDatabase: TeamDatabase): Boolean {
        return teamDatabase.idTeam != null

    }

    fun isFavorite(teamDatabase: TeamDatabase): Boolean {
        var mFavorite = false

        if (getMainActivity().viewModel.getIsFromAPI()) {
            val teamData = userRepository.readTeam(teamDatabase.idTeam!!)

            for (i in teamData.indices) {
                if (teamData[i].isFavorite != null) {
                    mFavorite = true

                }

            }

        } else {
            if (teamDatabase.isFavorite != null) {
                mFavorite = true

            }

        }

        return mFavorite

    }

    fun setFavorite(isFavorite: Boolean, teamDatabase: TeamDatabase) {
        var teamData = emptyList<TeamDatabase>()

        if (getMainActivity().viewModel.getIsFromAPI()) {
            teamData = userRepository.readTeam(teamDatabase.idTeam!!)

        }

        if (isFavorite) {
            createTeamDatabase(teamDatabase)

        } else {
            if (getMainActivity().viewModel.getIsFromAPI()) {
                for (i in teamData.indices) {
                    userRepository.deleteTeam(teamData[i])

                }

            } else {
                userRepository.deleteTeam(teamDatabase)

            }

        }

    }

    private fun createTeamDatabase(teamDatabase: TeamDatabase) {
        userRepository.createTeam(
            TeamDatabase(
                0,
                teamDatabase.idTeam,
                true,
                teamDatabase.strTeamBadge,
                teamDatabase.strTeam,
                teamDatabase.intFormedYear,
                teamDatabase.strStadium,
                teamDatabase.strDescriptionEN
            )
        )

    }

    fun getPlayerObjects(): MutableList<PlayerRecyclerViewAdapter.PlayerObject> {
        return playerObjects
    }

    fun initPlayerList(playerList: List<PlayerDetail>?) {
        playerObjects.clear()

        if (playerList != null) {
            for (i in playerList.indices) {
                playerObject = PlayerRecyclerViewAdapter.PlayerObject(playerList[i])
                playerObjects.add(playerObject)

            }

        }

    }

    fun requestPlayerList(responseListener: ResponseListener, teamName: String) {
        userRepository.requestPlayerList(responseListener, teamName)

    }

}
