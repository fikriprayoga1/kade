package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.database.EventDatabase
import com.example.footballmatchschedule.util.jetpack.UserRepository
import com.example.footballmatchschedule.util.recyclerviewadapter.EventRecyclerViewAdapter
import com.example.footballmatchschedule.MainActivity

class FavoriteEventViewModel : ViewModel() {
    // 1
    private var userRepository: UserRepository? = null

    // 2
    private var favoriteEvent: LiveData<List<EventDatabase>>? = null

    // 3
    private val eventObjects: MutableList<EventRecyclerViewAdapter.EventObject> = ArrayList()

    // 4
    private lateinit var eventObject: EventRecyclerViewAdapter.EventObject

    fun init(userRepository: UserRepository) {
        this.userRepository = userRepository

        if (favoriteEvent == null) {
            favoriteEvent = userRepository.databaseHandler.readFavoriteEvent()

        }

    }

    fun getFavoriteEvent(): LiveData<List<EventDatabase>>? {
        return favoriteEvent
    }

    fun initEventList(eventList: List<EventDatabase>?) {
        eventObjects.clear()

        if (eventList != null) {
            for (i in eventList.indices) {
                eventObject = EventRecyclerViewAdapter.EventObject(eventList[i])
                eventObjects.add(eventObject)

            }

        }

    }

    fun getEventObjects(): MutableList<EventRecyclerViewAdapter.EventObject> {
        return eventObjects
    }

}
