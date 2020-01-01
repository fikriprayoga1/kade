package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.EventDetail
import com.example.footballmatchschedule.model.database.EventDatabase
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.other.recyclerviewadapter.EventRecyclerViewAdapter
import com.example.footballmatchschedule.view.MainActivity

class FavoriteEventViewModel : ViewModel() {
    // 1
    private var userRepository: UserRepository? = null
    // 2
    private lateinit var mainActivity: MainActivity
    // 3
    private var favoriteEvent: LiveData<List<EventDatabase>>? = null
    // 4
    private val eventObjects: MutableList<EventRecyclerViewAdapter.EventObject> = ArrayList()
    // 5
    private lateinit var eventObject: EventRecyclerViewAdapter.EventObject

    fun init(userRepository: UserRepository, mainActivity: MainActivity) {
        this.userRepository = userRepository
        this.mainActivity = mainActivity

        if (favoriteEvent == null) {
            favoriteEvent = userRepository.readFavoriteEvent()

        }

    }
    fun getFavoriteEvent(): LiveData<List<EventDatabase>>? { return favoriteEvent }

    fun initEventList(eventList: List<EventDatabase>?) {
        eventObjects.clear()

        if (eventList != null) {
            for (i in eventList.indices) {
                eventObject = EventRecyclerViewAdapter.EventObject(eventList[i])
                eventObjects.add(eventObject)

            }

        }

    }

    fun getMainActivity(): MainActivity {
        return mainActivity
    }

    fun getEventObjects(): MutableList<EventRecyclerViewAdapter.EventObject> {
        return eventObjects
    }

}
