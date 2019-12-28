package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.EventDetail
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.other.recyclerviewadapter.EventRecyclerViewAdapter
import com.example.footballmatchschedule.view.MainActivity

class SearchEventViewModel : ViewModel() {
    // 1
    private lateinit var userRepository: UserRepository
    // 2
    private lateinit var mainActivity: MainActivity
    // 3
    private val eventObjects: MutableList<EventRecyclerViewAdapter.EventObject> =
        ArrayList()
    // 4
    private lateinit var eventObject: EventRecyclerViewAdapter.EventObject

    fun init(userRepository: UserRepository, mainActivity: MainActivity) {
        this.userRepository = userRepository
        this.mainActivity = mainActivity

    }

    fun getEventObjects(): MutableList<EventRecyclerViewAdapter.EventObject> {
        return eventObjects
    }

    fun initEventList(it: List<EventDetail>?) {
        eventObjects.clear()

        if (it != null) {
            for (i in it.indices) {
                eventObject = EventRecyclerViewAdapter.EventObject(it[i])
                eventObjects.add(eventObject)

            }

        }

    }

    fun getMainActivity(): MainActivity {
        return mainActivity
    }

}
