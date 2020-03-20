package com.example.footballmatchschedule.util.helper

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.footballmatchschedule.model.RetrofitResponse
import com.example.footballmatchschedule.model.apiresponse.EventDetail
import com.example.footballmatchschedule.model.apiresponse.PlayerDetail
import com.example.footballmatchschedule.model.apiresponse.TeamDetail
import com.example.footballmatchschedule.model.database.EventDatabase
import com.example.footballmatchschedule.model.database.TeamDatabase
import com.example.footballmatchschedule.util.jetpack.UserDatabase
import com.example.footballmatchschedule.util.jetpack.UserRepository
import com.example.footballmatchschedule.util.jetpack.Webservice
import retrofit2.Response

object FMSHelper {
    // 1
    private val queueInit: Short = 0
    // 2
    private var userRepository: UserRepository? = null
    // 3
    private var leagueIdHolder = MutableLiveData<String>()
    // 4
    private val eventList = MutableLiveData<List<EventDetail>>()
    // 5
    private val hasFragmentBackstack = mutableMapOf<String, Boolean>()
    // 6
    private val searchTeamList = MutableLiveData<List<TeamDetail>>()
    // 7
    private lateinit var selectedEvent: EventDatabase
    // 8
    private lateinit var selectedTeam: TeamDatabase
    // 9
    private lateinit var selectedPlayer: PlayerDetail
    // 10
    private var isFromAPI = true

    fun init(context: Context) {
        if (userRepository == null) {
            userRepository =
                UserRepository(Webservice.create(), UserDatabase.getInstance(context).userDao())

        }

    }

    fun getQueueInit(): Short {
        return queueInit
    }

    fun getUserRepository(): UserRepository {
        return userRepository!!
    }

    fun setLeagueIdHolder(idHolder: String) {
        leagueIdHolder.value = idHolder

    }

    fun getLeagueIdHolderListener(): LiveData<String> {
        return leagueIdHolder
    }

    fun getLeagueIdHolder(): String? {
        return leagueIdHolder.value
    }

    fun setEventList(eventList: List<EventDetail>?) {
        this.eventList.value = eventList

    }

    fun getEventList(): LiveData<List<EventDetail>>? {
        return eventList
    }

    fun setSearchTeamList(searchTeamList: List<TeamDetail>?) {
        this.searchTeamList.value = searchTeamList

    }

    fun getSearchTeamList(): LiveData<List<TeamDetail>>? {
        return searchTeamList
    }

    fun setHasFragmentBackstack(fragmentName: String, state: Boolean) {
        hasFragmentBackstack[fragmentName] = state
    }

    fun getHasFragmentBackstack(fragmentName: String): Boolean {
        return hasFragmentBackstack[fragmentName] ?: false
    }

    fun setSelectedEvent(selectedEvent: EventDatabase) {
        this.selectedEvent = selectedEvent

    }

    fun getSelectedEvent(): EventDatabase {
        return selectedEvent
    }

    fun setSelectedTeam(selectedTeam: TeamDatabase) {
        this.selectedTeam = selectedTeam

    }

    fun getSelectedTeam(): TeamDatabase {
        return selectedTeam
    }

    fun setIsFromAPI(isFromAPI: Boolean) {
        this.isFromAPI = isFromAPI
    }

    fun getIsFromAPI(): Boolean {
        return isFromAPI
    }

    fun setSelectedPlayer(playerDetail: PlayerDetail) {
        this.selectedPlayer = playerDetail
    }

    fun getSelectedPlayer(): PlayerDetail {
        return selectedPlayer
    }

    fun errorResponseHandler(
        responseListener: ResponseListener,
        t: Throwable,
        description: String
    ) {
        var message = description
        val mMessage = t.message
        val mCause = t.cause?.message

        if ((mMessage != null) && (mCause != null)) {
            message = "$mMessage # $mCause"
        } else {
            if (mMessage != null) {
                message = mMessage
            }

            if (mCause != null) {
                message = mCause
            }
        }

        responseListener.retrofitResponse(
            RetrofitResponse(
                false,
                message,
                null
            )
        )

    }

    fun responseHandlerOK(retrofitResponse: Any, responseListener: ResponseListener) {
        val response = retrofitResponse as Response<*>
        var isSuccess = false
        val message: String
        val rb = response.body()

        if (rb != null) {
            isSuccess = true
            message = "Request response is OK"

        } else {
            message = "Response body is null"
        }

        responseListener.retrofitResponse(
            RetrofitResponse(
                isSuccess,
                message,
                rb
            )
        )

    }

}