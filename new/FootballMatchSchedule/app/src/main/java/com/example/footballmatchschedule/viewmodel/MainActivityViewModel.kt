package com.example.footballmatchschedule.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.SearchEventDetail
import com.example.footballmatchschedule.model.apiresponse.SearchTeamDetail
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
    private val searchEventList = MutableLiveData<List<SearchEventDetail>>()
    // 6
    private val hasFragmentBackstack = mutableMapOf<String, Boolean>()
    // 7
    private val searchTeamList = MutableLiveData<List<SearchTeamDetail>>()

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

    fun setSearchEventList(searchEventList: List<SearchEventDetail>?) {
        this.searchEventList.value = searchEventList

    }

    fun getSearchEventList(): LiveData<List<SearchEventDetail>>? {
        return searchEventList
    }

    fun setSearchTeamList(searchTeamList: List<SearchTeamDetail>?) {
        this.searchTeamList.value = searchTeamList

    }

    fun getSearchTeamList(): LiveData<List<SearchTeamDetail>>? {
        return searchTeamList
    }

    fun setHasFragmentBackstack(fragmentName: String, state: Boolean) {
        hasFragmentBackstack[fragmentName] = state
    }

    fun getHasFragmentBackstack(fragmentName: String): Boolean {
        return hasFragmentBackstack.get(fragmentName) ?: false
    }

    fun setInit(view: View) {
        view.visibility = View.VISIBLE

    }

}