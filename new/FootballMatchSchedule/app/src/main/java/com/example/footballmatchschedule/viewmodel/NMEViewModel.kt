package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.EventDetail
import com.example.footballmatchschedule.other.helper.ResponseListener
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.other.recyclerviewadapter.EventRecyclerViewAdapter
import com.example.footballmatchschedule.view.MainActivity

class NMEViewModel : ViewModel() {
    // 1
    private lateinit var userRepository: UserRepository
    // 2
    private lateinit var mainActivity: MainActivity
    // 3
    private val nmeObjects: MutableList<EventRecyclerViewAdapter.EventObject> = ArrayList()
    // 4
    private lateinit var nmeObject: EventRecyclerViewAdapter.EventObject

    fun init(userRepository: UserRepository, mainActivity: MainActivity) {
        this.userRepository = userRepository
        this.mainActivity = mainActivity

    }

    fun initEventList(eventList: List<EventDetail>?) {
        nmeObjects.clear()

        if (eventList != null) {
            for (i in eventList.indices) {
                nmeObject = EventRecyclerViewAdapter.EventObject(eventList[i])
                nmeObjects.add(nmeObject)

            }

        }

    }

    fun requestEventList(responseListener: ResponseListener, id: String) {
        userRepository.requestNMEList(id, responseListener)

    }

    fun getMainActivity(): MainActivity {
        return mainActivity
    }

    fun getEventObjects(): MutableList<EventRecyclerViewAdapter.EventObject> {
        return nmeObjects
    }
}
