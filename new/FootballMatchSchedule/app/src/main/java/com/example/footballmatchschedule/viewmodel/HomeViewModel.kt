package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class HomeViewModel : ViewModel() {
    // 1
    private val job = Job()
    // 2
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    fun getJob(): Job { return job }

    fun getUIScope(): CoroutineScope { return uiScope }
}
