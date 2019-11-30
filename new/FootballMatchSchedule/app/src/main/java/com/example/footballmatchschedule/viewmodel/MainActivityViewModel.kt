package com.example.footballmatchschedule.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.footballmatchschedule.other.helper.Tag
import com.example.footballmatchschedule.other.jetpack.UserDatabase
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.other.jetpack.Webservice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.concurrent.Executors

class MainActivityViewModel: ViewModel() {
    // 1
    private var loadingQueue: Short = 0
    // 2
    private val queueInit: Short = 0
    // 3
    private var userRepository: UserRepository? = null
    // 4
    private var leagueIdHolder = MutableLiveData<String>()

    fun init(context: Context) {
        if (userRepository == null) {
            userRepository = UserRepository(
                Webservice.create(),
                Room.databaseBuilder(context, UserDatabase::class.java, "ajarin_aja").build().userDao())

        }

    }

    fun addLoading(): Short {
        loadingQueue++
        Log.d(Tag().tag, "MainActivityViewModel/33 : $loadingQueue")
        return loadingQueue

    }

    fun reduceLoading(): Short {
        loadingQueue--
        Log.d(Tag().tag, "MainActivityViewModel/33 : $loadingQueue")
        return loadingQueue

    }

    fun getQueueInit(): Short { return queueInit }

    fun getUserRepository(): UserRepository { return userRepository!! }

    fun setLeagueIdHolder(idHolder: String) { leagueIdHolder.value = idHolder }

    fun getLeagueIdHolder(): LiveData<String> { return leagueIdHolder }

    fun getLeagueIdHolder2(): String? { return leagueIdHolder.value }

}