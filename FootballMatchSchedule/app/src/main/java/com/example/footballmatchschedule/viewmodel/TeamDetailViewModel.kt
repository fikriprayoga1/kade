package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.PlayerDetail
import com.example.footballmatchschedule.model.database.TeamDatabase
import com.example.footballmatchschedule.util.helper.ResponseListener
import com.example.footballmatchschedule.util.jetpack.UserRepository
import com.example.footballmatchschedule.util.recyclerviewadapter.PlayerRecyclerViewAdapter
import com.example.footballmatchschedule.MainActivity
import com.example.footballmatchschedule.util.helper.FMSHelper

class TeamDetailViewModel : ViewModel() {
    // 1
    private lateinit var userRepository: UserRepository

    // 2
    private val playerObjects: MutableList<PlayerRecyclerViewAdapter.PlayerObject> = ArrayList()

    // 3
    private lateinit var playerObject: PlayerRecyclerViewAdapter.PlayerObject

    fun init(userRepository: UserRepository) {
        this.userRepository = userRepository

    }

    fun hasIdEvent(teamDatabase: TeamDatabase): Boolean {
        return teamDatabase.idTeam != null

    }

    fun isFavorite(teamDatabase: TeamDatabase): Boolean {
        var mFavorite = false

        if (FMSHelper.getIsFromAPI()) {
            val teamData = userRepository.databaseHandler.readTeam(teamDatabase.idTeam!!)

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

        if (FMSHelper.getIsFromAPI()) {
            teamData = userRepository.databaseHandler.readTeam(teamDatabase.idTeam!!)

        }

        if (isFavorite) {
            createTeamDatabase(teamDatabase)

        } else {
            if (FMSHelper.getIsFromAPI()) {
                for (i in teamData.indices) {
                    userRepository.databaseHandler.deleteTeam(teamData[i])

                }

            } else {
                userRepository.databaseHandler.deleteTeam(teamDatabase)

            }

        }

    }

    private fun createTeamDatabase(teamDatabase: TeamDatabase) {
        userRepository.databaseHandler.createTeam(
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
