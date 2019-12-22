package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.LMEDetail
import com.example.footballmatchschedule.other.ResponseListener
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.other.recyclerviewadapter.LMERecyclerViewAdapter
import com.example.footballmatchschedule.view.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class LMEViewModel : ViewModel() {
    // 1
    private lateinit var userRepository: UserRepository
    // 2
    private lateinit var mainActivity: MainActivity
    // 3
    private val lmeObjects: MutableList<LMERecyclerViewAdapter.LMEObject> = ArrayList()
    // 4
    private lateinit var lmeObject: LMERecyclerViewAdapter.LMEObject

    fun init(userRepository: UserRepository, mainActivity: MainActivity) {
        this.userRepository = userRepository
        this.mainActivity = mainActivity

    }

    fun initLMEList(lmeList: List<LMEDetail>) {
        lmeObjects.clear()

        for (i in lmeList.indices) {
            lmeObject = LMERecyclerViewAdapter.LMEObject(lmeList[i])
            lmeObjects.add(lmeObject)

        }

    }

    fun requestLMEList(id: String, responseListener: ResponseListener) {
        userRepository.requestLMEList(id, responseListener)

    }

    fun getMainActivity(): MainActivity {
        return mainActivity
    }

    fun getLMEObjects(): MutableList<LMERecyclerViewAdapter.LMEObject> {
        return lmeObjects
    }

}
