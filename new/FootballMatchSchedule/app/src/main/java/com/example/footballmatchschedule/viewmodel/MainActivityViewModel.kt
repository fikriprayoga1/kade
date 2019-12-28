package com.example.footballmatchschedule.viewmodel

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.footballmatchschedule.model.apiresponse.EventDetail
import com.example.footballmatchschedule.model.apiresponse.TeamDetail
import com.example.footballmatchschedule.other.jetpack.UserDatabase
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.other.jetpack.Webservice

class MainActivityViewModel : ViewModel() {
    // 1
    private var loadingQueue: Short = 0
    // 2
    private val queueInit: Short = 0
    // 3
    private var userRepository: UserRepository? = null
    // 4
    private var leagueIdHolder = MutableLiveData<String>()
    // 5
    private val eventList = MutableLiveData<List<EventDetail>>()
    // 6
    private val hasFragmentBackstack = mutableMapOf<String, Boolean>()
    // 7
    private val searchTeamList = MutableLiveData<List<TeamDetail>>()
    // 8
    private lateinit var selectedEvent: EventDetail

    fun init(context: Context) {
        if (userRepository == null) {
            userRepository = UserRepository(Webservice.create(), Room.databaseBuilder(context, UserDatabase::class.java, "football_match_schedule").build().userDao())

        }

    }

    fun updateLoading(isStartLoading: Boolean): Int {
        if (isStartLoading) {
            loadingQueue++
        } else {
            loadingQueue--
        }

        return if (loadingQueue == getQueueInit()) {
            View.GONE
        } else {
            View.VISIBLE
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

    fun setSelectedEvent(selectedEvent: EventDetail) {
        this.selectedEvent = selectedEvent

    }

    fun getSelectedEvent(): EventDetail { return selectedEvent }

}