package com.example.footballmatchschedule.viewmodel

import androidx.lifecycle.ViewModel
import com.example.footballmatchschedule.model.apiresponse.EventDetail
import com.example.footballmatchschedule.model.database.EventDatabase
import com.example.footballmatchschedule.util.jetpack.UserRepository
import com.example.footballmatchschedule.util.recyclerviewadapter.EventRecyclerViewAdapter
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
                eventObject = EventRecyclerViewAdapter.EventObject(getEventObject(it[i]))
                eventObjects.add(eventObject)

            }

        }

    }

    fun getMainActivity(): MainActivity {
        return mainActivity
    }

    private fun getEventObject(eventDetail: EventDetail): EventDatabase {
        return EventDatabase(
            0,
            eventDetail.dateEvent,
            eventDetail.idEvent!!,
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
