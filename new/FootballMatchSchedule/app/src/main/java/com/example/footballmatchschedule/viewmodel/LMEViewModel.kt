package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.EventDetail
import com.example.footballmatchschedule.other.ResponseListener
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.other.recyclerviewadapter.EventRecyclerViewAdapter
import com.example.footballmatchschedule.view.MainActivity

class LMEViewModel : ViewModel() {
    // 1
    private lateinit var userRepository: UserRepository
    // 2
    private lateinit var mainActivity: MainActivity
    // 3
    private val eventObjects: MutableList<EventRecyclerViewAdapter.EventObject> = ArrayList()
    // 4
    private lateinit var eventObject: EventRecyclerViewAdapter.EventObject

    fun init(userRepository: UserRepository, mainActivity: MainActivity) {
        this.userRepository = userRepository
        this.mainActivity = mainActivity

    }

    fun initEventList(eventList: List<EventDetail>?) {
        eventObjects.clear()

        if (eventList != null) {
            for (i in eventList.indices) {
                eventObject = EventRecyclerViewAdapter.EventObject(eventList[i])
                eventObjects.add(eventObject)

            }

        }

    }

    fun requestLMEList(responseListener: ResponseListener, id: String) {
        userRepository.requestLMEList(id, responseListener)

    }

    fun getMainActivity(): MainActivity {
        return mainActivity
    }

    fun getEventObjects(): MutableList<EventRecyclerViewAdapter.EventObject> {
        return eventObjects
    }

}
