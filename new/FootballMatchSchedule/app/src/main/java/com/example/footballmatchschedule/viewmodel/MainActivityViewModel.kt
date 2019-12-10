package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.other.jetpack.Webservice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

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
    private val job = Job()
    // 6
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    // 7
    private val tag = "football_match_schedule"

    fun init() {
        if (userRepository == null) {
            userRepository = UserRepository(Webservice.create())

        }

    }

    fun addLoading(): Short {
        loadingQueue++
        return loadingQueue

    }

    fun reduceLoading(): Short {
        loadingQueue--
        return loadingQueue

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

    fun getJob(): Job {
        return job
    }

    fun getUIScope(): CoroutineScope {
        return uiScope
    }

    fun getTag(): String {
        return tag
    }

}