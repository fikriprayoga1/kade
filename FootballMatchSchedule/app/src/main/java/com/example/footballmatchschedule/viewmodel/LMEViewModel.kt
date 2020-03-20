package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.EventDetail
import com.example.footballmatchschedule.model.database.EventDatabase
import com.example.footballmatchschedule.util.helper.ResponseListener
import com.example.footballmatchschedule.util.jetpack.UserRepository
import com.example.footballmatchschedule.util.recyclerviewadapter.EventRecyclerViewAdapter
import com.example.footballmatchschedule.MainActivity

class LMEViewModel : ViewModel() {
    // 1
    private lateinit var userRepository: UserRepository

    // 2
    private val eventObjects: MutableList<EventRecyclerViewAdapter.EventObject> = ArrayList()

    // 3
    private lateinit var eventObject: EventRecyclerViewAdapter.EventObject

    fun init(userRepository: UserRepository) {
        this.userRepository = userRepository

    }

    fun initEventList(eventList: List<EventDetail>?) {
        eventObjects.clear()

        if (eventList != null) {
            for (i in eventList.indices) {
                eventObject = EventRecyclerViewAdapter.EventObject(getEventObject(eventList[i]))
                eventObjects.add(eventObject)

            }

        }

    }

    fun requestLMEList(responseListener: ResponseListener, id: String) {
        userRepository.requestLMEList(id, responseListener)

    }

    fun getEventObjects(): MutableList<EventRecyclerViewAdapter.EventObject> {
        return eventObjects
    }

    private fun getEventObject(eventDetail: EventDetail): EventDatabase {
        return EventDatabase(
            0,
            eventDetail.dateEvent,
            eventDetail.idEvent,
            eventDetail.strHomeTeam,
            eventDetail.strAwayTeam,
            eventDetail.intHomeScore,
            eventDetail.intAwayScore,
            eventDetail.idHomeTeam,
            eventDetail.idAwayTeam,
            eventDetail.intHomeShots,
            eventDetail.intAwayShots,
            eventDetail.strHomeGoalDetails,
            eventDetail.strAwayGoalDetails,
            eventDetail.strHomeLineupGoalkeeper,
            eventDetail.strAwayLineupGoalkeeper,
            eventDetail.strHomeLineupDefense,
            eventDetail.strAwayLineupDefense,
            eventDetail.strHomeLineupMidfield,
            eventDetail.strAwayLineupMidfield,
            eventDetail.strHomeLineupForward,
            eventDetail.strAwayLineupForward,
            eventDetail.strHomeLineupSubstitutes,
            eventDetail.strAwayLineupSubstitutes,
            eventDetail.strLeague,
            eventDetail.strEvent,
            null,
            null
        )

    }

}
