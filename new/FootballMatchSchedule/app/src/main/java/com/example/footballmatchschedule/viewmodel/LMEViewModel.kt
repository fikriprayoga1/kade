package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.EventDetail
import com.example.footballmatchschedule.other.ResponseListener
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.other.recyclerviewadapter.LMERecyclerViewAdapter
import com.example.footballmatchschedule.view.MainActivity

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

    fun initEventList(eventList: List<EventDetail>?) {
        lmeObjects.clear()

        if (eventList != null) {
            for (i in eventList.indices) {
                lmeObject = LMERecyclerViewAdapter.LMEObject(eventList[i])
                lmeObjects.add(lmeObject)

            }

        }

    }

    fun requestLMEList(responseListener: ResponseListener, id: String) {
        userRepository.requestLMEList(id, responseListener)

    }

    fun getMainActivity(): MainActivity {
        return mainActivity
    }

    fun getLMEObjects(): MutableList<LMERecyclerViewAdapter.LMEObject> {
        return lmeObjects
    }

}
