package com.example.footballmatchschedule.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.EventDetail
import com.example.footballmatchschedule.model.apiresponse.TeamDetail
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
    private val searchEventList = MutableLiveData<List<EventDetail>>()
    // 6
    private val hasFragmentBackstack = mutableMapOf<String, Boolean>()
    // 7
    private val searchTeamList = MutableLiveData<List<TeamDetail>>()
    // 8
    private lateinit var selectedEvent: EventDetail

    fun init() {
        if (userRepository == null) {
            userRepository = UserRepository(Webservice.create())

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

    fun setSearchEventList(searchEventList: List<EventDetail>?) {
        this.searchEventList.value = searchEventList

    }

    fun getSearchEventList(): LiveData<List<EventDetail>>? {
        return searchEventList
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
        return hasFragmentBackstack.get(fragmentName) ?: false
    }

    fun setSelectedEvent(selectedEvent: EventDetail) {
        this.selectedEvent = selectedEvent

    }

    fun getSelectedEvent(): EventDetail { return selectedEvent }

}